package com.gmail.markushygedombrowski.pickaxe;

import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Pickaxe {
    private final String name;
    private final String lore;
    private final Material material;
    private final int digSpeed;


    public Pickaxe(String name, String lore, Material material, int digSpeed) {
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.digSpeed = digSpeed;
    }

    public ItemStack getPickaxeItem(PlayerInfo playerInfo) {
        ItemStack pickaxe = new ItemStack(material);
        ItemMeta metaPickaxe = pickaxe.getItemMeta();
        metaPickaxe.setDisplayName(name.replace("%lvl%", String.valueOf(playerInfo.getPicklvl())));
        metaPickaxe.setLore(Utils.convertStringToList(lore));
        pickaxe.setItemMeta(metaPickaxe);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, digSpeed);
        return pickaxe;
    }
}
