package com.spigot.plugin.blockshuffle2.commands;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;

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
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 1){
            //TODO completion on tab
        }

        return null;
    }
}
