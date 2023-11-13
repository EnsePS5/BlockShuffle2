package com.spigot.plugin.blockshuffle2;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public enum PlayerSpecialization {
    ENGINEER,MINER,WARRIOR,WIZARD;

    PlayerSpecialization(){}

    List<ItemStack> playerEquipment(PlayerSpecialization specialization){

        ItemStack SHEARS = new ItemStack(Material.SHEARS);
        SHEARS.addUnsafeEnchantment(Enchantment.LUCK, 1);
        SHEARS.addEnchantment(Enchantment.DURABILITY,10);
        SHEARS.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        SHEARS.getItemMeta().setDisplayName(ConstantUtils.SHEARS_NAME);

        ItemStack STICK_BONE_MEAL = new ItemStack(Material.BONE_MEAL, 64);
        STICK_BONE_MEAL.addUnsafeEnchantment(Enchantment.LUCK, 1);
        STICK_BONE_MEAL.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        STICK_BONE_MEAL.getItemMeta().setDisplayName(ConstantUtils.BONE_MEAL_NAME);

        ItemStack POWERUP_SHULKER_BOX = new ItemStack(Material.SHULKER_BOX);
        POWERUP_SHULKER_BOX.addUnsafeEnchantment(Enchantment.LUCK, 1);
        POWERUP_SHULKER_BOX.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        POWERUP_SHULKER_BOX.getItemMeta().setDisplayName(ConstantUtils.POWERUP_SHULKER_BOX_NAME);

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
                PICKAXE_SILK_TOUCH.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
                PICKAXE_SILK_TOUCH.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
                PICKAXE_SILK_TOUCH.getItemMeta().setLore(ConstantUtils.PICKAXE_SILK_TOUCH_LORE);
                PICKAXE_SILK_TOUCH.getItemMeta().setDisplayName(ConstantUtils.PICKAXE_SILK_TOUCH_NAME);

                potionMeta.addCustomEffect(
                        new PotionEffect(PotionEffectType.SLOW_FALLING, PotionEffect.INFINITE_DURATION, 4)
                        , false);
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
                WIZARD_BOOK.addUnsafeEnchantment(Enchantment.LUCK, 1);
                WIZARD_BOOK.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
                WIZARD_BOOK.getItemMeta().setLore(ConstantUtils.WIZARD_BOOK_LORE);
                WIZARD_BOOK.getItemMeta().setDisplayName(ConstantUtils.WIZARD_BOOK_NAME);

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
                ItemStack MONSTER_EGG_SPAWNER = ConstantUtils.MONSTER_EGG_SPAWNER;
                MONSTER_EGG_SPAWNER.addUnsafeEnchantment(Enchantment.LUCK, 1);
                MONSTER_EGG_SPAWNER.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
                MONSTER_EGG_SPAWNER.getItemMeta().setLore(ConstantUtils.MONSTER_EGG_SPAWNER_LORE);
                MONSTER_EGG_SPAWNER.getItemMeta().setDisplayName(ConstantUtils.MONSTER_EGG_SPAWNER_NAME);

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
                itemStacksToAdd.add(MONSTER_EGG_SPAWNER);
                break;
            }
            case ENGINEER: {
                ItemStack BLOCK_COPIER = ConstantUtils.BLOCK_COPIER;
                BLOCK_COPIER.addUnsafeEnchantment(Enchantment.LUCK, 1);
                BLOCK_COPIER.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
                BLOCK_COPIER.getItemMeta().setLore(ConstantUtils.BLOCK_COPIER_LORE);
                BLOCK_COPIER.getItemMeta().setDisplayName(ConstantUtils.BLOCK_COPIER_NAME);

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

