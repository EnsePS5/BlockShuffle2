package com.spigot.plugin.blockshuffle2.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    List<PotionEffect> potionEffects = new ArrayList<>();
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        potionEffects.clear();
        event.setKeepInventory(true);
        event.getDrops().clear();
        potionEffects.addAll(event.getEntity().getActivePotionEffects());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        System.out.println(potionEffects);
        potionEffects.forEach(potionEffect -> {
            potionEffect.apply(event.getPlayer());
        });
    }
}
