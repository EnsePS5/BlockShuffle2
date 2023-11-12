package com.spigot.plugin.blockshuffle2.tasks;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.ConstantUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerTask {

    public TimerTask(){
        new BukkitRunnable() {

            int timeLeft = ConstantUtils.ROUND_TIME_IN_SECONDS;
            int minutes = timeLeft /60;
            int seconds = timeLeft %60;
            @Override
            public void run() {

                while (!(minutes == 0 && seconds == 0)) {
                    minutes = timeLeft / 60;
                    seconds = timeLeft % 60;

                    for (Player player : BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME) {
                        if (minutes >= 3) {
                            player.spigot().sendMessage(
                                    ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "" + minutes + " : " + seconds));
                        } else if (minutes >= 1) {
                            player.spigot().sendMessage(
                                    ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "" + minutes + " : " + seconds));
                        } else if (minutes == 0 && seconds > 10) {
                            player.spigot().sendMessage(
                                    ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "" + minutes + " : " + seconds));
                        } else if (seconds <= 10 && seconds >= 0) {
                            for (Player player1 : BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME) {
                                player1.sendTitle((ChatColor.DARK_RED + "" + seconds), null, 1, 18, 1);
                                player1.playSound(player1.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, .5f, .5f);
                            }
                        }
                    }

                    try {
                        Thread.sleep(1000);
                        timeLeft--;

                    } catch (InterruptedException e) {
                        return;
                    }
                }

                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ChatColor.ITALIC + "Time's up!");

                BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME.stream().forEach(p -> {
                    if (true) //TODO CHANGE THIS
                        p.sendTitle((ChatColor.DARK_RED + "" + ChatColor.BOLD + "TIME'S UP!"), "Your objective is fulfilled", 5, 60, 15);
                    else {
                        p.sendTitle((ChatColor.DARK_RED + "" + ChatColor.BOLD + "TIME'S UP!"), "-1 point", 5, 60, 15);
                        p.playSound(p.getLocation(),Sound.ENTITY_WITCH_DEATH,.6f,.6f);
                    }
                });
            }
        }.runTaskAsynchronously(BlockShuffle2.getPlugin(BlockShuffle2.class));
    }
}
