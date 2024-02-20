package com.spigot.plugin.blockshuffle2.powerups;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PowerUp extends ItemStack {

    private String displayName;
    private List<String> lore;
    private Rarity rarity;
    private String nameId;
    public PowerUp(Material material, String displayName, List<String> lore, Rarity rarity, String nameId){
        super(material);
        this.displayName = displayName;
        this.lore = lore;
        this.rarity = rarity;
        this.nameId = nameId;

        this.setPowerUpMeta();
    }

    private void setPowerUpMeta(){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(Rarity.getColorByRarity(this.rarity) + "( " + this.rarity.toString() + " ) " + this.displayName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        this.setItemMeta(itemMeta);
    }

    public String getNameId() {
        return this.nameId;
    }

    public static void givePowerUp(Player player, PowerUp powerUp){
        if (powerUp == null)
            player.spigot().sendMessage(ChatMessageType.CHAT , TextComponent.fromLegacyText((ChatColor.RED + "Powerup not found.")));

        player.getInventory().addItem(powerUp);
    }
}
