package com.spigot.plugin.blockshuffle2.listeners;

import com.spigot.plugin.blockshuffle2.BlockShuffle2;
import com.spigot.plugin.blockshuffle2.PlayerSpecialization;
import com.spigot.plugin.blockshuffle2.PowerUpShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

import static com.spigot.plugin.blockshuffle2.BlockShuffle2.PLAYED_ROUNDS;
import static com.spigot.plugin.blockshuffle2.BlockShuffle2.PLAYER_READY;
import static com.spigot.plugin.blockshuffle2.ConstantUtils.*;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent playerDropItemEvent){

        PlayerSpecialization playerSpecialization = null;

        if (BlockShuffle2.PLAYED_ROUNDS == 0) {

            switch (playerDropItemEvent.getItemDrop().getItemStack().getItemMeta().getDisplayName()) {
                case BLOCK_COPIER_NAME: {
                    playerSpecialization = PlayerSpecialization.ENGINEER;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);

                    playerSpecialization.playerEquipment(PlayerSpecialization.ENGINEER).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case WARRIOR_EGG_SPAWNER_NAME: {
                    playerSpecialization = PlayerSpecialization.WARRIOR;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);

                    playerSpecialization.playerEquipment(PlayerSpecialization.WARRIOR).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case WIZARD_BOOK_NAME: {
                    playerSpecialization = PlayerSpecialization.WIZARD;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);

                    playerSpecialization.playerEquipment(PlayerSpecialization.WIZARD).forEach(
                            i -> playerDropItemEvent.getPlayer().getInventory().addItem(i)
                    );
                    break;
                }
                case PICKAXE_SILK_TOUCH_NAME: {
                    playerSpecialization = PlayerSpecialization.MINER;
                    playerDropItemEvent.getPlayer().getInventory().clear();

                    PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);

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
