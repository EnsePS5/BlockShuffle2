package com.spigot.plugin.blockshuffle2;

import com.spigot.plugin.blockshuffle2.commands.CommandManager;
import com.spigot.plugin.blockshuffle2.tasks.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.WorldBorder;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.spigot.plugin.blockshuffle2.ConstantUtils.WORLD_NAME;
import static com.spigot.plugin.blockshuffle2.ConstantUtils.WORLD_SIZE;

public final class BlockShuffle2 extends JavaPlugin implements Listener {

    //Command Manager
    private static CommandManager commandManager;
    private static ConsoleCommandSender consoleCommandSender;

    //Player Scores and Values
    public static int PLAYED_ROUNDS = 0;
    public static final List<Player> PLAYERS_TAKING_PART_IN_THE_GAME = new ArrayList<>();
    public static final Map<Player, ItemStack> PLAYER_GOALS = new HashMap<>();
    public static final Map<Player, Score> PLAYER_SCORES = new HashMap<>();

    //Score table
    private static Objective objective;
    private static Scoreboard scoreboard;

    //World Border and Blocks
    private static WorldBorder worldBorder;
    private static final List<Material> AllMaterials = List.of(Material.values());
    private static final List<Chunk> AllChunksInBorder = new ArrayList<>();

    @Override
    public void onEnable() {

        System.out.println(BlockShuffle2.class.getName() + " launch. Initialization...");

        commandManager = new CommandManager();
        consoleCommandSender = Bukkit.getServer().getConsoleSender();

        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("bs2")).setExecutor(commandManager);
        Objects.requireNonNull(this.getCommand("bs2")).setTabCompleter(commandManager);

        prepareScoreTableOnServerStart();

        try {
            worldBorder = Objects.requireNonNull(getServer().getWorld(WORLD_NAME)).getWorldBorder();
            worldBorder.setCenter(Objects.requireNonNull(getServer().getWorld("world")).getSpawnLocation());
        }catch (NullPointerException e){
            throw new NullPointerException("World name should be " + WORLD_NAME);
        }

        System.out.println("Initialization Successful");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void run() {

        if (PLAYED_ROUNDS == 0) {
            prepareScoreTableOnPluginStart();
            preparePlayersOnPluginStart();
            prepareWorldOnPluginStart();
        }
        new TimerTask();
    }

    private static void prepareWorldOnPluginStart() {

        worldBorder.setSize(1000);
        worldBorder.setDamageAmount(100);
        worldBorder.setWarningDistance(16);
        worldBorder.setWarningTime(4);

        System.out.println("Starting World Initialization...");
        ServerMessage("Starting World Initialization...");
        ServerMessageUrgent("PLEASE DO NOT MOVE, SERVER WILL BE FROZEN FOR BETTER PERFORMANCE");
        //TODO zczytać bloki z okolicy

        getAllChunksFromBorder(Bukkit.getServer());
        preparePlayableBlocks(Bukkit.getServer());

        System.out.println("World Initialized Successfully");
        ServerMessage("World Initialized Successfully!");

        System.out.println(AllChunksInBorder);

    }

    private static void preparePlayableBlocks(Server server) {

        for (Material material : AllMaterials){
            
            
            
        }
    }

    private static void getAllChunksFromBorder(Server server) {

        double percentageCounter = 0;

        for (int x = -(WORLD_SIZE / 2) + 8; x < (WORLD_SIZE / 2); x = x + 16)
        {
            for (int y = -(WORLD_SIZE / 2) + 8; y < (WORLD_SIZE / 2); y = y + 16)
            {
                for (int z = server.getWorld(WORLD_NAME).getMinHeight();
                     z <= server.getWorld(WORLD_NAME).getMaxHeight();
                     z = z + server.getWorld(WORLD_NAME).getMaxHeight() + 64) {
                    AllChunksInBorder.add(new Location(server.getWorld(WORLD_NAME), x, y, z).getChunk());
                    System.out.println("Added chunk at x,y,z -> " + x +" "+ y +" "+ z);
                }
            }
            percentageCounter++;
            System.out.println("---- <" + Math.rint(percentageCounter/((double) WORLD_SIZE /16))*100 + "%> ----");
            ServerMessage("---- <" + Math.rint(percentageCounter/((double) WORLD_SIZE /16))*100 + "%> ----");
        }

    }

    private static void prepareScoreTableOnServerStart() {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("points", "dummy");

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
