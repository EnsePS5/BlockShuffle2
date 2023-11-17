package com.spigot.plugin.blockshuffle2.powerups;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON, RARE, EPIC, FORGOTTEN, OVERPOWERED;

    public static ChatColor getColorByRarity(Rarity rarity){

        ChatColor color = null;

        if (rarity == (COMMON))
            color = ChatColor.GRAY;

        if (rarity == RARE)
            color = ChatColor.BLUE;

        if (rarity == EPIC)
            color = ChatColor.LIGHT_PURPLE;

        if (rarity == FORGOTTEN)
            color = ChatColor.GOLD;

        if (rarity == OVERPOWERED)
            color = ChatColor.RED;

        return color;
    }
}
