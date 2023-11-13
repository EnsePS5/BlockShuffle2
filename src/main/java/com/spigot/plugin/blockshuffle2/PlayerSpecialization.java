package com.spigot.plugin.blockshuffle2;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public enum PlayerSpecialization {
    ENGINEER,MINER,WARRIOR,WIZARD;

    PlayerSpecialization(){}

    public List<ItemStack> playerEquipment(PlayerSpecialization specialization){

        ItemMeta itemMeta;

        ItemStack SHEARS = new ItemStack(Material.SHEARS);
        itemMeta = SHEARS.getItemMeta();

        SHEARS.addUnsafeEnchantment(Enchantment.LUCK, 1);
        SHEARS.addEnchantment(Enchantment.DURABILITY,3);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ConstantUtils.SHEARS_NAME);

        SHEARS.setItemMeta(itemMeta);

        ItemStack STICK_BONE_MEAL = new ItemStack(Material.BONE_MEAL, 64);
        itemMeta = STICK_BONE_MEAL.getItemMeta();

        STICK_BONE_MEAL.addUnsafeEnchantment(Enchantment.LUCK, 1);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ConstantUtils.BONE_MEAL_NAME);

        STICK_BONE_MEAL.setItemMeta(itemMeta);

        ItemStack POWERUP_SHULKER_BOX = new ItemStack(Material.SHULKER_BOX);
        itemMeta = POWERUP_SHULKER_BOX.getItemMeta();

        POWERUP_SHULKER_BOX.addUnsafeEnchantment(Enchantment.LUCK, 1);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ConstantUtils.POWERUP_SHULKER_BOX_NAME);

        POWERUP_SHULKER_BOX.setItemMeta(itemMeta);

        List<ItemStack> itemStacksToAdd = new ArrayList<>();
        itemStacksToAdd.add(SHEARS);
        itemStacksToAdd.add(STICK_BONE_MEAL);
        itemStacksToAdd.add(POWERUP_SHULKER_BOX);

        ItemStack POTION = new ItemStack(Material.POTION);

        PotionMeta potionMeta = (PotionMeta) POTION.getItemMeta();
        potionMeta.setColor(Color.BLACK);

        switch (specialization){
            case MINER: {
                ItemStack PICKAXE_SILK_TOUCH = ConstantUtils.PICKAXE_SILK_TOUCH;
                itemMeta = PICKAXE_SILK_TOUCH.getItemMeta();
                PICKAXE_SILK_TOUCH.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setLore(ConstantUtils.PICKAXE_SILK_TOUCH_LORE);
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + ConstantUtils.PICKAXE_SILK_TOUCH_NAME);
                PICKAXE_SILK_TOUCH.setItemMeta(itemMeta);

                ItemStack FEATHER_FALLING_BOOTS = ConstantUtils.FEATHER_FALLING_BOOTS;
                itemMeta = FEATHER_FALLING_BOOTS.getItemMeta();
                FEATHER_FALLING_BOOTS.addEnchantment(Enchantment.PROTECTION_FALL,4);
                FEATHER_FALLING_BOOTS.addEnchantment(Enchantment.DURABILITY, 3);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + ConstantUtils.FEATHER_FALLING_BOOTS_NAME);
                FEATHER_FALLING_BOOTS.setItemMeta(itemMeta);


                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.FAST_DIGGING, PotionEffect.INFINITE_DURATION, 2)
                        , false);
                potionMeta.setDisplayName(specialization + " PERK");
                POTION.setItemMeta(potionMeta);

                itemStacksToAdd.add(POTION);
                itemStacksToAdd.add(PICKAXE_SILK_TOUCH);
                break;
            }
            case WIZARD: {
                ItemStack WIZARD_BOOK = ConstantUtils.WIZARD_BOOK;
                itemMeta = WIZARD_BOOK.getItemMeta();
                WIZARD_BOOK.addUnsafeEnchantment(Enchantment.LUCK, 1);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setLore(ConstantUtils.WIZARD_BOOK_LORE);
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + ConstantUtils.WIZARD_BOOK_NAME);
                WIZARD_BOOK.setItemMeta(itemMeta);

                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 2)
                        , false);
                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1)
                        , false);
                potionMeta.setDisplayName(specialization + " PERK");
                POTION.setItemMeta(potionMeta);

                itemStacksToAdd.add(POTION);
                itemStacksToAdd.add(WIZARD_BOOK);
                break;
            }
            case WARRIOR: {
                ItemStack WARRIOR_EGG_SPAWNER = ConstantUtils.WARRIOR_EGG_SPAWNER;
                itemMeta = WARRIOR_EGG_SPAWNER.getItemMeta();
                WARRIOR_EGG_SPAWNER.addUnsafeEnchantment(Enchantment.LUCK, 1);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setLore(ConstantUtils.WARRIOR_EGG_SPAWNER_LORE);
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + ConstantUtils.WARRIOR_EGG_SPAWNER_NAME);
                WARRIOR_EGG_SPAWNER.setItemMeta(itemMeta);

                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.HEALTH_BOOST, PotionEffect.INFINITE_DURATION, 2),
                        false);
                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, 2),
                        false
                );
                potionMeta.setDisplayName(specialization + " PERK");
                POTION.setItemMeta(potionMeta);

                itemStacksToAdd.add(POTION);
                itemStacksToAdd.add(WARRIOR_EGG_SPAWNER);
                break;
            }
            case ENGINEER: {
                ItemStack BLOCK_COPIER = ConstantUtils.BLOCK_COPIER;
                itemMeta = BLOCK_COPIER.getItemMeta();
                BLOCK_COPIER.addUnsafeEnchantment(Enchantment.LUCK, 1);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.setLore(ConstantUtils.BLOCK_COPIER_LORE);
                itemMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + ConstantUtils.BLOCK_COPIER_NAME);
                BLOCK_COPIER.setItemMeta(itemMeta);

                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 2)
                        , false);
                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.SATURATION, PotionEffect.INFINITE_DURATION, 2)
                        , false);
                potionMeta.setDisplayName(specialization + " PERK");
                POTION.setItemMeta(potionMeta);

                itemStacksToAdd.add(POTION);
                itemStacksToAdd.add(BLOCK_COPIER);
                break;
            }
        }
        return itemStacksToAdd;
    }

}

