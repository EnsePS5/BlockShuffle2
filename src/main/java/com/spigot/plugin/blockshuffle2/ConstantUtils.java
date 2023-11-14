package com.spigot.plugin.blockshuffle2;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConstantUtils {

    public static final int ROUND_TIME_IN_SECONDS = 300;
    public static final int MAX_ROUNDS = 7;
    public static final String WORLD_NAME = "world";
    public static final int WORLD_SIZE = 1024;

    //Item specialization
    public static final String SHEARS_NAME = "STARTER SHEARS";
    public static final String BONE_MEAL_NAME = "STARTER BONE_MEAL";
    public static final String PICKAXE_SILK_TOUCH_NAME = "MINER SILK TOUCH";
    public static final List<String> PICKAXE_SILK_TOUCH_LORE = List.of(
            "MINERS ARE ABLE TO JUMP DOWN FROM HIGH ALTITUDES!",
            "MOREOVER YOU HAVE GRATER CHANCE TO GET BLOCK THAN MOB DROP",
            "THEY MINE FASTER TOO."
    );
    public static final String WIZARD_BOOK_NAME = "WIZARD BLOCK BOOK";
    public static final List<String> WIZARD_BOOK_LORE = List.of(
            "WIZARDS ARE ABLE TO CONJURE UP RANDOM BLOCKS AND ITEMS",
            " + REGENERATE AT THE SAME TIME!",
            "THEY SEE BRIGHTER AT NIGHT TOO."
    );
    public static final String POWERUP_SHULKER_BOX_NAME = "POWERUPS";
    public static final String WARRIOR_EGG_SPAWNER_NAME = "MONSTER SPAWNER EGG";
    public static final List<String> WARRIOR_EGG_SPAWNER_LORE = List.of(
            "WARRIORS CAN CHOOSE WHAT MOB TO SPAWN AT ANY TIME!",
            "THEY HAVE GRATER CHANCE TO GET MOB DROP THAN BLOCK",
            "AND DEALS GRATER DAMAGE. ADDITIONALLY YOU CAN WITHSTAND MUCH MORE!"
    );
    public static final String BLOCK_COPIER_NAME = "BLOCK COPIER";
    public static final List<String> BLOCK_COPIER_LORE = List.of(
            "ENGINEERS CAN COPY ANY BLOCK OR ITEM IN THEIRS INVENTORY!",
            "THEY DON'T NEED TO EAT AND ARE MUCH MORE DAMAGE RESISTANT THAN OTHERS"
    );
    public static final ItemStack BLOCK_COPIER = new ItemStack(Material.NAME_TAG);
    public static final ItemStack WARRIOR_EGG_SPAWNER = new ItemStack(Material.EGG);
    public static final ItemStack WIZARD_BOOK = new ItemStack(Material.WRITTEN_BOOK);
    public static final ItemStack PICKAXE_SILK_TOUCH = new ItemStack(Material.DIAMOND_PICKAXE);
    public static final ItemStack FEATHER_FALLING_BOOTS = new ItemStack(Material.LEATHER_BOOTS);
    public static final String FEATHER_FALLING_BOOTS_NAME = "MINERS BOOTS";
    public static final Material LEFT_CHOICE = Material.GREEN_WOOL;
    public static final Material RIGHT_CHOICE = Material.RED_WOOL;
}
