package com.spigot.plugin.blockshuffle2;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

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
                    playerSpecialization.playerEquipment(PlayerSpecialization.ENGINEER);
                    break;
                }
                case MONSTER_EGG_SPAWNER_NAME: {
                    playerSpecialization = PlayerSpecialization.WARRIOR;
                    playerSpecialization.playerEquipment(PlayerSpecialization.WARRIOR);
                    break;
                }
                case WIZARD_BOOK_NAME: {
                    playerSpecialization = PlayerSpecialization.WIZARD;
                    playerSpecialization.playerEquipment(PlayerSpecialization.WIZARD);
                    break;
                }
                case PICKAXE_SILK_TOUCH_NAME: {
                    playerSpecialization = PlayerSpecialization.MINER;
                    playerSpecialization.playerEquipment(PlayerSpecialization.MINER);
                    break;
                }
            }
            PLAYER_READY.put(playerDropItemEvent.getPlayer(), true);

            if (!PLAYER_READY.containsValue(false)) {
                PLAYED_ROUNDS++;
                BlockShuffle2.run();
            }
        }
    }
}
