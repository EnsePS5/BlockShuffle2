package com.spigot.plugin.blockshuffle2;

import com.spigot.plugin.blockshuffle2.commands.CommandManager;
import com.spigot.plugin.blockshuffle2.listeners.InventoryQuitListener;
import com.spigot.plugin.blockshuffle2.listeners.ItemDropListener;
import com.spigot.plugin.blockshuffle2.tasks.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.WorldBorder;
import org.bukkit.block.Biome;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.BiomeSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.spigot.plugin.blockshuffle2.ConstantUtils.*;

public final class BlockShuffle2 extends JavaPlugin implements Listener {

    //Command Manager
    private static CommandManager commandManager;
    private static ConsoleCommandSender consoleCommandSender;

    //Player Scores and Values
    public static int PLAYED_ROUNDS = 0;
    public static final List<Player> PLAYERS_TAKING_PART_IN_THE_GAME = new ArrayList<>();
    public static final Map<Player, ItemStack> PLAYER_GOALS = new HashMap<>();
    public static final Map<Player, Score> PLAYER_SCORES = new HashMap<>();
    public static final Map<Player, Boolean> PLAYER_READY = new HashMap<>();

    //Power ups content
    public static final Map<Player, Inventory> PLAYER_POWER_UPS = new HashMap<>();

    //Score table
    private static Objective objective;
    private static Scoreboard scoreboard;

    //World Border and Blocks
    private static WorldBorder worldBorder;
    private static final List<Material> AllMaterials = List.of(Material.values());
    private static final List<Chunk> AllChunksInBorder = new ArrayList<>();
    private static final Set<Biome> AllBiomesInChunks = new HashSet<>();
    private static Map<Biome, Set<Material>> BLOCKS_AVAILABLE_IN_GAME;
    private static final PlayableBlocks playableBlocksManager = PlayableBlocks.getInstance();

    @Override
    public void onEnable() {

        System.out.println(BlockShuffle2.class.getName() + " launch. Initialization...");

        commandManager = new CommandManager();
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("bs2")).setExecutor(commandManager);
        Objects.requireNonNull(this.getCommand("bs2")).setTabCompleter(commandManager);

        consoleCommandSender = Bukkit.getServer().getConsoleSender();

        prepareScoreTableOnServerStart();
        new BlockShuffleLibrary();

        try {
            worldBorder = Objects.requireNonNull(getServer().getWorld(WORLD_NAME)).getWorldBorder();
            worldBorder.setCenter(Objects.requireNonNull(getServer().getWorld(WORLD_NAME)).getSpawnLocation());
        }catch (NullPointerException e){
            throw new NullPointerException("World name should be " + WORLD_NAME);
        }

        getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryQuitListener(), this);
        System.out.println("Initialization Successful");
    }
    //TODO System losowania bloków
    //TODO System głosowania na następną część bloków do dodania - 2 (IN PROGRESS)
    //TODO Powerupy i pomysły na nie
    //TODO Pozwolić powerupom tylko na wejście do shulkerboxa
    //TODO Przypisywanie bloków do graczy
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void run() {

        if (PLAYED_ROUNDS == 0) {
            prepareScoreTableOnPluginStart();
            preparePlayersOnPluginStart();
            prepareWorldOnPluginStart();
            chooseSpecialization();

            return;
        }
        new TimerTask();
    }

    private static void chooseSpecialization() {

        ItemMeta itemMeta;

        ItemStack bc = BLOCK_COPIER;
        itemMeta = bc.getItemMeta();
        itemMeta.setLore(BLOCK_COPIER_LORE);
        itemMeta.setDisplayName(BLOCK_COPIER_NAME);
        bc.setItemMeta(itemMeta);

        ItemStack mes = WARRIOR_EGG_SPAWNER;
        itemMeta = mes.getItemMeta();
        itemMeta.setLore(WARRIOR_EGG_SPAWNER_LORE);
        itemMeta.setDisplayName(WARRIOR_EGG_SPAWNER_NAME);
        mes.setItemMeta(itemMeta);

        ItemStack wb = WIZARD_BOOK;
        itemMeta = wb.getItemMeta();
        itemMeta.setLore(WIZARD_BOOK_LORE);
        itemMeta.setDisplayName(WIZARD_BOOK_NAME);
        wb.setItemMeta(itemMeta);

        ItemStack pst = PICKAXE_SILK_TOUCH;
        itemMeta = pst.getItemMeta();
        itemMeta.setLore(PICKAXE_SILK_TOUCH_LORE);
        itemMeta.setDisplayName(PICKAXE_SILK_TOUCH_NAME);
        pst.setItemMeta(itemMeta);
        Result result = new Result(bc, mes, wb, pst);

        for (Player player : PLAYERS_TAKING_PART_IN_THE_GAME){

            player.getInventory().addItem(
                    result.bc,
                    result.mes,
                    result.wb,
                    result.pst
            );
        }

    }

    private static class Result {
        public final ItemStack bc;
        public final ItemStack mes;
        public final ItemStack wb;
        public final ItemStack pst;

        public Result(ItemStack bc, ItemStack mes, ItemStack wb, ItemStack pst) {
            this.bc = bc;
            this.mes = mes;
            this.wb = wb;
            this.pst = pst;
        }
    }

    private static void prepareWorldOnPluginStart() {

        worldBorder.setSize(WORLD_SIZE);
        worldBorder.setDamageAmount(100);
        worldBorder.setWarningDistance(16);
        worldBorder.setWarningTime(4);

        System.out.println("Starting World Initialization...");
        ServerMessage("Starting World Initialization...");
        ServerMessageUrgent("PLEASE DO NOT MOVE, SERVER WILL BE FROZEN FOR BETTER PERFORMANCE");

        getAllChunksAndBiomesFromBorder(Bukkit.getServer());
        BlockShuffleLibrary.prepareBlocks();

        BLOCKS_AVAILABLE_IN_GAME = playableBlocksManager.filterOutUnusedBiomes(AllBiomesInChunks);

        System.out.println("World Initialized Successfully");
        ServerMessage("World Initialized Successfully!");
    }

    private static void getAllChunksAndBiomesFromBorder(Server server) {

        double percentageCounter = 0;

        for (int x = -(WORLD_SIZE / 2) + 8; x < (WORLD_SIZE / 2); x = x + 16)
        {
            for (int y = -(WORLD_SIZE / 2) + 8; y < (WORLD_SIZE / 2); y = y + 16)
            {
                Location location = new Location(server.getWorld(WORLD_NAME), x, y, 64);
                AllChunksInBorder.add(location.getChunk());
            }
            percentageCounter++;
            System.out.println("---- <" + String.format("%.2f", percentageCounter/((double) WORLD_SIZE /16)*100) + "%> ----");
            ServerMessage("---- <" + String.format("%.2f", percentageCounter/((double) WORLD_SIZE /16)*100) + "%> ----");
        }

        System.out.println("Getting Biomes in range...");
        ServerMessage("Getting Biomes in range...");

        for (Biome biome : Biome.values()) {
            BiomeSearchResult biomeSearchResult = Objects.requireNonNull(server.getWorld(WORLD_NAME)).locateNearestBiome(
                    Objects.requireNonNull(server.getWorld(WORLD_NAME)).getSpawnLocation(), (WORLD_SIZE / 2), biome);

            if (biomeSearchResult != null) {

                Location location = biomeSearchResult.getLocation();

                System.out.println(biome + " dis -> " + location.distance(server.getWorld(WORLD_NAME).getSpawnLocation()));
                if (location.distance(server.getWorld(WORLD_NAME).getSpawnLocation()) <= (double) WORLD_SIZE / 2) {
                    System.out.println("Qualified! Added to list");
                    AllBiomesInChunks.add(biome);
                }
            }
        }

        System.out.println("Biomes Registered!");
        ServerMessage("Biomes Registered!");

    }

    private static void prepareScoreTableOnServerStart() {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        try {
            assert scoreboardManager != null;
        }catch (NullPointerException e){
            throw new RuntimeException("Scoreboard could not been assign");
        }

        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("points", Criteria.DUMMY ,"dummy");

    }

    private static void prepareScoreTableOnPluginStart() {

        System.out.println("Starting Table Initialization...");
        ServerMessage("Starting Table Initialization...");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "Points/Round 1");

        System.out.println("Table Initialized Successful");
        ServerMessage("Table Initialized Successfully");
    }

    private static void preparePlayersOnPluginStart() {

        System.out.println("Starting Players Initialization...");
        ServerMessage("Starting Players Initialization...");

        PLAYERS_TAKING_PART_IN_THE_GAME.addAll(Bukkit.getOnlinePlayers());

        for (Player player : PLAYERS_TAKING_PART_IN_THE_GAME)
            PLAYER_READY.put(player, false);

        PLAYERS_TAKING_PART_IN_THE_GAME.forEach(p -> {
            Score score = objective.getScore(ChatColor.YELLOW + p.getDisplayName() + ChatColor.GOLD + " -> ");
            score.setScore(0);

            PLAYER_SCORES.put(p, score);

            p.setScoreboard(scoreboard);
        });

        System.out.println("Players Initialized Successful");
        ServerMessage("Players Initialized Successfully");
    }

    private static void ServerMessage(String message){
        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + message);
    }

    private static void ServerMessageUrgent(String message){
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.ITALIC + message);
    }

}
