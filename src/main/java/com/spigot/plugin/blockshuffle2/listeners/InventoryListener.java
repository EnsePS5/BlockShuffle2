package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.powerups.PowerUp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onPowerUpsInventoryQuit(InventoryCloseEvent event){

        if (BlockShuffle2.PLAYER_POWER_UPS.get(event.getPlayer()) == null)
            return;

        if (event.getInventory().getSize() ==
                BlockShuffle2.PLAYER_POWER_UPS.get(event.getPlayer()).getSize()){
            BlockShuffle2.PLAYER_POWER_UPS.put((Player) event.getPlayer() , event.getInventory());
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null)
            return;

        if (!BlockShuffle2.PLAYER_POWER_UPS.values().stream().findAny().isPresent()){
            return;
        }

        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) &&
                BlockShuffle2.PLAYER_POWER_UPS.values().stream().findAny().get().getSize() == event.getClickedInventory().getSize()){
            if (!event.getCurrentItem().getClass().equals(PowerUp.class)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrag(InventoryDragEvent event){

        System.out.println("dzieje się drag");

    }
}
