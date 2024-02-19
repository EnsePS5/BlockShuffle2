package com.spigot.plugin.blockshuffle2.commands;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.libraries.PowerUpLibrary;
import com.spigot.plugin.blockshuffle2.powerups.PowerUp;
import org.bukkit.Bukkit;
import org.bukkit.RegionAccessor;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private static ConsoleCommandSender console = null;

    public CommandManager(){
        console = Bukkit.getServer().getConsoleSender();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender.isOp()){
            switch (args.length){
                case 0: {
                    BlockShuffle2.run();
                    break;
                }
                case 1: {
                    switch (args[0]){
                        case "terminate" : {
                            BlockShuffle2.terminate();
                            break;
                        }
                    }
                }
                case 2: {
                    switch (args[0]){
                        case "timeSet" : {
                            BlockShuffle2.timerTask.setTimeLeft(Integer.parseInt(args[1]));
                            break;
                        }
                        case "powerups" : {
                            PowerUp.givePowerUp((Player) commandSender, PowerUpLibrary.POWER_UPS_IN_GAME.stream().filter(
                                    powerUp -> powerUp.getDisplayName().startsWith(args[1])).findFirst().get()); //TODO more than one arg
                            break;
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        List<String> adders = new ArrayList<>();

        if (args.length == 1){
            adders.add("terminate");
            adders.add("timeSet");
            adders.add("powerups");
        }

        if (args.length == 2 && args[0].equals("powerups")){
            PowerUpLibrary.POWER_UPS_IN_GAME.forEach(powerUp ->
                    adders.add(powerUp.getDisplayName()));
        }

        return adders;
    }
}
