package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.ConstantUtils;
import com.spigot.plugin.blockshuffle2.powerups.PowerUp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

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


        if (isPlayersInventoryClickedWhenPowerUpInventoryIsOpenAndItemIsClicked(event)){
            if (!event.getCurrentItem().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_DESTROYS)){
                event.setCancelled(true);
                BlockShuffle2.ServerMessageUrgent("You can only move power-ups when POWER-UP Box is open");
            }
        }
    }

    private boolean isPlayersInventoryClickedWhenPowerUpInventoryIsOpenAndItemIsClicked(InventoryClickEvent event){
        return Arrays.stream(InventoryAction.values()).anyMatch(e -> e.equals(event.getAction())) &&
                ConstantUtils.POWER_UPS_BOX_SIZE == event.getInventory().getSize() &&
                event.getClickedInventory().getType().equals(InventoryType.PLAYER);
    }
}
