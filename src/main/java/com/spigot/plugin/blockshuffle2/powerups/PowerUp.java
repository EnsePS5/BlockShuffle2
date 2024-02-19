package com.spigot.plugin.blockshuffle2.powerups;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

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

    public String getDisplayName() {
        return displayName;
    }

    public static void givePowerUp(Player player, PowerUp powerUp){
        if (powerUp == null)
            player.spigot().sendMessage(ChatMessageType.CHAT , TextComponent.fromLegacyText((ChatColor.RED + "Powerup not found.")));

        player.getInventory().addItem(powerUp);
    }
}
