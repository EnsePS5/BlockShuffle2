package com.spigot.plugin.blockshuffle2;

import com.spigot.plugin.blockshuffle2.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BlockShuffle2 extends JavaPlugin {

    private static CommandManager commandManager;
    private static final List<Player> playersTakingPartInGame = new ArrayList<>();

    @Override
    public void onEnable() {

        System.out.println(BlockShuffle2.class.getName() + " launch. Initialization...");

        commandManager = new CommandManager();

        System.out.println("Initialization Successful");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void run() {

        prepareGameToStart();

    }

    private static void prepareGameToStart() {

        playersTakingPartInGame.addAll(Bukkit.getOnlinePlayers());
        System.out.println(Arrays.toString(Material.values()));

    }


}
