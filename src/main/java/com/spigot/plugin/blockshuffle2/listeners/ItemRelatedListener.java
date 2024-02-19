package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.PlayerSpecialization;
import com.spigot.plugin.blockshuffle2.powerups.PowerUpShulkerBox;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

import static com.spigot.plugin.blockshuffle2.BlockShuffle2.*;
import static com.spigot.plugin.blockshuffle2.ConstantUtils.*;

public class ItemRelatedListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent playerInteractEvent){

        if (!playerInteractEvent.hasItem())
            return;

        if (VOTING){

            playerInteractEvent.setCancelled(true);
            final int[] counter = {0};

            switch (Objects.requireNonNull(playerInteractEvent.getItem()).getType()){
                case RED_WOOL: {
                    playerInteractEvent.getItem();
                    playerInteractEvent.getPlayer().getInventory().clear();

                    PLAYER_INVENTORY.get(playerInteractEvent.getPlayer()).values().forEach(i -> {
                        playerInteractEvent.getPlayer().getInventory().setItem(counter[0], i);
                        counter[0]++;
                    });

                    playerInteractEvent.getPlayer().sendMessage(
                            playerInteractEvent.getPlayer().getDisplayName() + ChatColor.DARK_RED + " voted for " + VOTING_BIOMES[1].getKey());

                    VOTE_COUNT++;
                    PLAYER_READY.put(playerInteractEvent.getPlayer(), false);

                    break;
                }
                case GREEN_WOOL: {
                    playerInteractEvent.getItem();
                    playerInteractEvent.getPlayer().getInventory().clear();

                    PLAYER_INVENTORY.get(playerInteractEvent.getPlayer()).values().forEach(i -> {
                        playerInteractEvent.getPlayer().getInventory().setItem(counter[0], i);
                        counter[0]++;
                    });
                    //TODO jak na nic nie zagłosuje, cofnąć eq
                    playerInteractEvent.getPlayer().sendMessage(
                            playerInteractEvent.getPlayer().getDisplayName() + ChatColor.DARK_GREEN + " voted for " + VOTING_BIOMES[0].getKey());

                    VOTE_COUNT--;
                    PLAYER_READY.put(playerInteractEvent.getPlayer(), false);

                    break;
                }
                default: break;
            }

            if (!PLAYER_READY.containsValue(true)){
                timerTask.cancel();
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent playerDropItemEvent){

        PlayerSpecialization playerSpecialization = null;

        if (BlockShuffle2.PLAYED_ROUNDS == 0) {

            switch (playerDropItemEvent.getItemDrop().getItemStack().getItemMeta().getDisplayName()) {
                case BLOCK_COPIER_NAME: {
                    playerSpecialization = PlayerSpecialization.ENGINEER;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);
                    PLAYERS_SPECS.put(playerDropItemEvent.getPlayer(), playerSpecialization);

                    playerSpecialization.playerEquipment(PlayerSpecialization.ENGINEER).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case WARRIOR_EGG_SPAWNER_NAME: {
                    playerSpecialization = PlayerSpecialization.WARRIOR;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);
                    PLAYERS_SPECS.put(playerDropItemEvent.getPlayer(), playerSpecialization);

                    playerSpecialization.playerEquipment(PlayerSpecialization.WARRIOR).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case WIZARD_BOOK_NAME: {
                    playerSpecialization = PlayerSpecialization.WIZARD;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);
                    PLAYERS_SPECS.put(playerDropItemEvent.getPlayer(), playerSpecialization);

                    playerSpecialization.playerEquipment(PlayerSpecialization.WIZARD).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case PICKAXE_SILK_TOUCH_NAME: {
                    playerSpecialization = PlayerSpecialization.MINER;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);
                    PLAYERS_SPECS.put(playerDropItemEvent.getPlayer(), playerSpecialization);

                    playerSpecialization.playerEquipment(PlayerSpecialization.MINER).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
            }

            playerDropItemEvent.getItemDrop().setTicksLived(5999);

            if (!PLAYER_READY.containsValue(false)) {
                PLAYED_ROUNDS++;
                BlockShuffle2.run();
            }
        }

        switch (playerDropItemEvent.getItemDrop().getItemStack().getItemMeta().getDisplayName()){
            case POWERUP_SHULKER_BOX_NAME: {
                playerDropItemEvent.setCancelled(true);
                Inventory inventory = PowerUpShulkerBox.createInventory(playerDropItemEvent.getPlayer());
                playerDropItemEvent.getPlayer().openInventory(inventory);
            }
        }
    }
}
