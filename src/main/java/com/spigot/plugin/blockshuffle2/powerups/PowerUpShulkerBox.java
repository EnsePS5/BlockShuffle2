package com.spigot.plugin.blockshuffle2.powerups;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class PowerUpShulkerBox {

    public static Inventory createInventory(Player player){
        Inventory powerUpSlots = Bukkit.createInventory(player, 18, ChatColor.DARK_PURPLE +
                player.getDisplayName().toUpperCase() + " POWER UPS");

        if (BlockShuffle2.PLAYER_POWER_UPS.containsKey(player)){

            Arrays.stream(BlockShuffle2.PLAYER_POWER_UPS.get(player).getContents()).forEach(i -> {
                if (i != null)
                    powerUpSlots.addItem(i);
            });
        } else {
            BlockShuffle2.PLAYER_POWER_UPS.put(player, powerUpSlots);
        }

        return powerUpSlots;
    }
}
