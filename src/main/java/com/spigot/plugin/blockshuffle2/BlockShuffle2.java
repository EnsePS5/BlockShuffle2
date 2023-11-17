package com.spigot.plugin.blockshuffle2;

import com.spigot.plugin.blockshuffle2.commands.CommandManager;
import com.spigot.plugin.blockshuffle2.libraries.BlockShuffleLibrary;
import com.spigot.plugin.blockshuffle2.listeners.InventoryListener;
import com.spigot.plugin.blockshuffle2.listeners.ItemDropListener;
import com.spigot.plugin.blockshuffle2.listeners.ItemGetListener;
import com.spigot.plugin.blockshuffle2.listeners.PlayerListener;
import com.spigot.plugin.blockshuffle2.tasks.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
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
    public static final Map<Player, Material> PLAYER_GOALS = new HashMap<>();
    public static final Map<Player, Score> PLAYER_SCORES = new HashMap<>();
    public static final Map<Player, Boolean> PLAYER_READY = new HashMap<>();
    public static TimerTask timerTask;

    //Voting
    public static boolean VOTING = false;
    public static int VOTE_COUNT = 0;
    public static Biome[] VOTING_BIOMES = new Biome[2];

    //Power ups content
    public static final Map<Player, Inventory> PLAYER_POWER_UPS = new HashMap<>();
    public static final Map<Player, Map<Integer, ItemStack>> PLAYER_INVENTORY = new HashMap<>();

    //Score table
    private static Objective objectivePoints;
    private static Objective objectiveItems;
    private static Scoreboard scoreboard;
    public static int maxScoreToGet = 0;

    //World Border and Blocks
    private static WorldBorder worldBorder;
    private static final List<Chunk> AllChunksInBorder = new ArrayList<>();
    private static final Set<Biome> AllBiomesInBorder = new HashSet<>();
    private static Map<NamespacedKey, Set<Material>> BLOCKS_TAKING_PART_IN_THE_GAME;
    private static List<Material> BlocksInGame = new ArrayList<>();
    private static final PlayableBlocks playableBlocksManager = PlayableBlocks.getInstance();
    public static final Map<Player, PlayerSpecialization> PLAYERS_SPECS = new HashMap<>();

    public static void voteWinner(Biome votingBiome) {


        if (votingBiome == null){

            BlocksInGame.addAll(BLOCKS_TAKING_PART_IN_THE_GAME.get(VOTING_BIOMES[0].getKey()));
            BlocksInGame.addAll(BLOCKS_TAKING_PART_IN_THE_GAME.get(VOTING_BIOMES[1].getKey()));

            PLAYERS_TAKING_PART_IN_THE_GAME.forEach(p ->
                    p.sendTitle(ChatColor.WHITE + "TIE!",ChatColor.LIGHT_PURPLE + "Both biomes are being added!",5,60,15));
            System.out.println("TIE!" + " Both biomes are being added!");

            BLOCKS_TAKING_PART_IN_THE_GAME.remove(VOTING_BIOMES[0].getKey());
            BLOCKS_TAKING_PART_IN_THE_GAME.remove(VOTING_BIOMES[1].getKey());

        }else {
            BlocksInGame.addAll(BLOCKS_TAKING_PART_IN_THE_GAME.get(votingBiome.getKey()));
            PLAYERS_TAKING_PART_IN_THE_GAME.forEach(p ->
                    p.sendTitle(ChatColor.WHITE + votingBiome.getKey().getKey() + " WINS!",
                            ChatColor.LIGHT_PURPLE + votingBiome.getKey().getKey() + " is being added!",5,60,15));
            System.out.println(votingBiome.getKey().getKey() + " WINS! " + votingBiome.getKey().getKey() + " is being added!");

            BLOCKS_TAKING_PART_IN_THE_GAME.remove(votingBiome.getKey());
        }

        System.out.println("Current biomes left in pool -> " + BLOCKS_TAKING_PART_IN_THE_GAME);
    }

    public static void voteCount() {

        VOTING=false;

        if (VOTE_COUNT > 0){
            BlockShuffle2.voteWinner(VOTING_BIOMES[0]);
            return;
        }
        if (VOTE_COUNT < 0){
            BlockShuffle2.voteWinner(VOTING_BIOMES[1]);
            return;
        }
        BlockShuffle2.voteWinner(null);

    }

    public static void terminate() {
        PLAYER_SCORES.clear();
        PLAYED_ROUNDS = 0;
        PLAYER_INVENTORY.clear();
        PLAYERS_TAKING_PART_IN_THE_GAME.clear();
        PLAYER_READY.clear();
        PLAYER_POWER_UPS.clear();
        PLAYERS_SPECS.clear();
        AllBiomesInBorder.clear();
        AllChunksInBorder.clear();
        timerTask.cancel();
        BLOCKS_TAKING_PART_IN_THE_GAME.clear();
        BlocksInGame.clear();
        VOTING = false;

        ServerMessageUrgent("GAME TERMINATED!");
    }

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
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemGetListener(), this);
        System.out.println("Initialization Successful");
    }
    //TODO Powerupy i pomysły na nie (IN PROGRESS)
    //TODO Pozwolić powerupom tylko na wejście do shulkerboxa (IN PROGRESS)
    //TODO Loggi (IN PROGRESS)
    //TODO Dodać info o bloku do szukania pod tabem
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

        if (VOTING){

            if (BLOCKS_TAKING_PART_IN_THE_GAME.size() <= 2){
                VOTING = false;
                return;
            }

            prepareVotingForPlayers();

            timerTask.cancel();
            timerTask = new TimerTask();
            timerTask.setTimeLeft(40);

            return;
        }


        if (PLAYED_ROUNDS <= MAX_ROUNDS) {

            System.out.println("Round " + PLAYED_ROUNDS + "!\nBlocks in game -> " + BlocksInGame);

            if (PLAYED_ROUNDS == MAX_ROUNDS){
                objectivePoints.setDisplayName(ChatColor.GOLD + "Points/" + ChatColor.RED + "Last Round");
            }else {
                objectivePoints.setDisplayName(ChatColor.GOLD + "Points/Round " + PLAYED_ROUNDS);
            }

            maxScoreToGet = PLAYERS_TAKING_PART_IN_THE_GAME.size();
            assignRandomBlocksToPlayers();

            if (PLAYED_ROUNDS != 1) {
                timerTask.cancel();
            }

            timerTask = new TimerTask();

            return;
        }
        //WINNING CONDITION

        int maxScore = -999;
        Player winningPlayer = null;

        for (Player player : PLAYER_SCORES.keySet()){
            if (PLAYER_SCORES.get(player).getScore() > maxScore){
                maxScore = PLAYER_SCORES.get(player).getScore();
                winningPlayer = player;
            }//TODO overtime
        }

        final Player champion = winningPlayer;

        PLAYERS_TAKING_PART_IN_THE_GAME.forEach(player -> {
            player.sendTitle(ChatColor.GOLD + champion.getDisplayName() + " WINS!", "Congratulations!", 5, 80, 15);

            if (player != champion){
                player.playSound(player,Sound.ENTITY_CREEPER_HURT,.6f,.6f);
                player.playSound(player,Sound.ENTITY_VEX_CHARGE, .8f,.3f);
                player.playSound(player,Sound.ENTITY_VEX_HURT,.2f,.8f);
            }

            player.getInventory().clear();
            player.getActivePotionEffects().clear();
        });

        champion.playSound(champion.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.5f, 1.5f);
        champion.playSound(champion.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE,.45f,.65f);
        champion.spawnParticle(Particle.TOTEM,champion.getLocation(),250);
        champion.spawnParticle(Particle.FLASH,champion.getLocation().add(0,10,0),100);

        terminate();
    }

    private static void prepareVotingForPlayers() {

        ItemStack LEFT_CHOICE = new ItemStack(ConstantUtils.LEFT_CHOICE);
        ItemStack RIGHT_CHOICE = new ItemStack(ConstantUtils.RIGHT_CHOICE);

        List<Biome> biomesTemp = new ArrayList<>(AllBiomesInBorder);

        int index = (int)Math.random() * biomesTemp.size();
        ItemMeta itemMeta = LEFT_CHOICE.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + biomesTemp.get(index).toString().toUpperCase());
        LEFT_CHOICE.setItemMeta(itemMeta);

        VOTING_BIOMES[0] = biomesTemp.get(index);

        biomesTemp.remove(index);

        index = (int)Math.random() * biomesTemp.size();

        itemMeta = RIGHT_CHOICE.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_RED + biomesTemp.get(index).toString().toUpperCase());
        RIGHT_CHOICE.setItemMeta(itemMeta);

        VOTING_BIOMES[1] = biomesTemp.get(index);

        System.out.println("Voting between " + VOTING_BIOMES[0] + " and " + VOTING_BIOMES[1]);

        Map<Integer,ItemStack> items = new HashMap<>();
        final int[] counter = {0};

        for (Player player : PLAYERS_TAKING_PART_IN_THE_GAME) {

            player.getInventory().forEach(item -> {
                items.put(counter[0], item);
                counter[0]++;
            });
            player.sendMessage(ChatColor.YELLOW + "Press 'Q' on wool of your choice!");
            player.sendTitle(ChatColor.LIGHT_PURPLE + "Vote for biome!",
                    ChatColor.DARK_GREEN + "" + VOTING_BIOMES[0] + " vs " + ChatColor.DARK_RED + VOTING_BIOMES[1],5,60,15);

            PLAYER_INVENTORY.put(player, items);
            player.getInventory().clear();
            player.getInventory().setItem(1, LEFT_CHOICE);
            player.getInventory().setItem(7, RIGHT_CHOICE);

            counter[0] = 0;
        }
    }

    private static void assignRandomBlocksToPlayers() {

        Material materialToAssign;
        int index = 0;

        for (Player player : PLAYERS_TAKING_PART_IN_THE_GAME){

            index = (int)(Math.random() * BlocksInGame.size());

            materialToAssign = BlocksInGame.get(index);
            PLAYER_GOALS.put(player, materialToAssign);
            System.out.println(player.getDisplayName() + " -> " + materialToAssign + "(" + index + " from " + BlocksInGame.size() + ")");

            PLAYER_READY.put(player, false);

            player.sendMessage(ChatColor.YELLOW + "Item to obtain is -> " + PLAYER_GOALS.get(player));
            player.sendTitle(ChatColor.WHITE + "" + PLAYER_GOALS.get(player),ChatColor.YELLOW + "is the Item to obtain!",5,60,15);
            player.playSound(player, Sound.BLOCK_BONE_BLOCK_HIT, 0.7f, 0.5f);
        }
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

        for (Player player : PLAYERS_TAKING_PART_IN_THE_GAME){

            player.getInventory().addItem(
                    bc,
                    mes,
                    wb,
                    pst
            );
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

        BLOCKS_TAKING_PART_IN_THE_GAME = playableBlocksManager.filterOutUnusedBiomes(AllBiomesInBorder);
        BlocksInGame.addAll(BlockShuffleLibrary.GENERAL);

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
                    AllBiomesInBorder.add(biome);
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
        objectivePoints = scoreboard.registerNewObjective("points", Criteria.DUMMY ,"dummy");
        objectiveItems = scoreboard.registerNewObjective("items", Criteria.DUMMY, "dummy"); //TODO dodać item pod tabem

    }

    private static void prepareScoreTableOnPluginStart() {

        System.out.println("Starting Table Initialization...");
        ServerMessage("Starting Table Initialization...");

        objectivePoints.setDisplaySlot(DisplaySlot.SIDEBAR);
        objectivePoints.setDisplayName(ChatColor.GOLD + "Points/Round 1");

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
            Score score = objectivePoints.getScore(ChatColor.YELLOW + p.getDisplayName() + ChatColor.GOLD + " -> ");
            score.setScore(0);

            PLAYER_SCORES.put(p, score);

            p.setScoreboard(scoreboard);
            p.getInventory().clear();
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
