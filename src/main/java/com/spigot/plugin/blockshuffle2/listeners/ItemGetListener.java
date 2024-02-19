package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.Locale;

public class ItemGetListener implements Listener {
    @EventHandler
    public void onItemGet(EntityPickupItemEvent event){

        if (BlockShuffle2.PLAYED_ROUNDS == 0){
            return;
        }

        for (Player player : BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME){

            if (BlockShuffle2.PLAYER_GOALS.get(player) == event.getItem().getItemStack().getType()){
                 BlockShuffle2.PLAYER_READY.put(player, true);

                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + player.getDisplayName().toUpperCase(Locale.ROOT) +
                        " OBTAINED " + BlockShuffle2.PLAYER_GOALS.get(player) + "!");

                player.spawnParticle(Particle.TOTEM,player.getLocation(),150);
                player.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "ITEM OBTAINED", "+" +
                        BlockShuffle2.maxScoreToGet + " point(s)",5,60,15);

                BlockShuffle2.PLAYER_SCORES.get(player).setScore(
                        BlockShuffle2.PLAYER_SCORES.get(player).getScore() + BlockShuffle2.maxScoreToGet);

                BlockShuffle2.maxScoreToGet--;

                BlockShuffle2.PLAYERS_TAKING_PART_IN_THE_GAME.forEach(
                        p -> p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 0.5f, 0.5f));

                //TODO system przyznawania powerupów
            }

            if (!BlockShuffle2.PLAYER_READY.containsValue(false)){
                BlockShuffle2.VOTING = true;
                BlockShuffle2.PLAYED_ROUNDS++;
                BlockShuffle2.run();
            }
        }
    }
}
