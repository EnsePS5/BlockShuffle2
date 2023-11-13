package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryQuitListener implements Listener {

    @EventHandler
    public void onPowerUpsInventoryQuit(InventoryCloseEvent event){

        if (BlockShuffle2.PLAYER_POWER_UPS.get(event.getPlayer()) == null)
            return;

        if (event.getInventory().getSize() ==
                BlockShuffle2.PLAYER_POWER_UPS.get(event.getPlayer()).getSize()){
            BlockShuffle2.PLAYER_POWER_UPS.put((Player) event.getPlayer() , event.getInventory());
        }
    }

}
