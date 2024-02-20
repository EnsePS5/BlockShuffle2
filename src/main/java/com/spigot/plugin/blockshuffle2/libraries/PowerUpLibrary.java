package com.spigot.plugin.blockshuffle2.libraries;

import com.spigot.plugin.blockshuffle2.ConstantUtils;
import com.spigot.plugin.blockshuffle2.powerups.PowerUp;
import com.spigot.plugin.blockshuffle2.powerups.Rarity;
import org.bukkit.Material;

import java.util.List;

public class PowerUpLibrary {

    public static final List<PowerUp> POWER_UPS_IN_GAME = List.of(
            new PowerUp(Material.WITHER_ROSE, "Rose of death", List.of(
                    "Choose another player and reduce their HP by 50%"
            ), Rarity.EPIC, "Rose-of-death"),
            new PowerUp(ConstantUtils.BLOCK_COPIER.getType(), ConstantUtils.BLOCK_COPIER_NAME, ConstantUtils.BLOCK_COPIER_LORE, Rarity.FORGOTTEN, "Block-copier"),
            new PowerUp(ConstantUtils.PICKAXE_SILK_TOUCH.getType(), ConstantUtils.PICKAXE_SILK_TOUCH_NAME, ConstantUtils.PICKAXE_SILK_TOUCH_LORE, Rarity.FORGOTTEN, "Miner-silk-touch"),
            new PowerUp(ConstantUtils.WIZARD_BOOK.getType(), ConstantUtils.WIZARD_BOOK_NAME, ConstantUtils.WIZARD_BOOK_LORE, Rarity.FORGOTTEN, "Wizard-block-book"),
            new PowerUp(ConstantUtils.WARRIOR_EGG_SPAWNER.getType(), ConstantUtils.WARRIOR_EGG_SPAWNER_NAME, ConstantUtils.WARRIOR_EGG_SPAWNER_LORE, Rarity.FORGOTTEN, "Monster-spawner-egg")
    );
}
