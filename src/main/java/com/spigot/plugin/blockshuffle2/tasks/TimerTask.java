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
import org.bukkit.scheduler.BukkitTask;

public class TimerTask {

    private BukkitTask task = null;
    private final boolean[] isCancelled = {false};
    private final int[] timeLeft = {ConstantUtils.ROUND_TIME_IN_SECONDS};

    public TimerTask(){

        this.isCancelled[0] = false;
        task = new BukkitRunnable() {

            @Override
             public void run() {

                int minutes = timeLeft[0] /60;
                int seconds = timeLeft[0] %60;

                while (!(minutes == 0 && seconds == 0) && !isCancelled[0]) {
                    minutes = timeLeft[0] / 60;
                    seconds = timeLeft[0] % 60;

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
                        timeLeft[0]--;

                    } catch (InterruptedException e) {
                        return;
                    }
                }

                if (isCancelled[0]){
                    isCancelled[0] = false;
                    return;
                }

                if (BlockShuffle2.VOTING){
                    BlockShuffle2.voteCount();
                    return;
                }

                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ChatColor.ITALIC + "Time's up!");

                BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME.forEach(p -> {
                    if (BlockShuffle2.PLAYER_READY.get(p))
                        p.sendTitle((ChatColor.DARK_RED + "" + ChatColor.BOLD + "TIME'S UP!"), "Your objective is fulfilled", 5, 60, 15);
                    else {
                        p.sendTitle((ChatColor.DARK_RED + "" + ChatColor.BOLD + "TIME'S UP!"), "-1 point", 5, 60, 15);
                        p.playSound(p.getLocation(),Sound.ENTITY_WITCH_DEATH,.6f,.6f);
                        BlockShuffle2.PLAYER_READY.put(p, true);
                    }
                });

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (BlockShuffle2.PLAYED_ROUNDS <= ConstantUtils.MAX_ROUNDS){
                    BlockShuffle2.VOTING = true;
                    BlockShuffle2.PLAYED_ROUNDS++;
                    BlockShuffle2.run();
                }
                this.cancel();
            }
        }.runTaskAsynchronously(BlockShuffle2.getPlugin(BlockShuffle2.class));
    }

    public void cancel() {
        task.cancel();
        this.isCancelled[0] = true;
    }

    public void setTimeLeft(int time) {
        timeLeft[0] = time;
    }
}
