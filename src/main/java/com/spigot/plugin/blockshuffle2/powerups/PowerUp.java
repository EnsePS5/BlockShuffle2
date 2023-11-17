package com.spigot.plugin.blockshuffle2.powerups;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PowerUp extends ItemStack {

    private String displayName;
    private List<String> lore;
    private Rarity rarity;
    public PowerUp(Material material, String displayName, List<String> lore, Rarity rarity){
        super(material);
        this.displayName = displayName;
        this.lore = lore;
        this.rarity = rarity;

        this.setItemMeta();
    }

    private void setItemMeta(){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(Rarity.getColorByRarity(rarity) + displayName);
        itemMeta.setLore(lore);
    }
}
