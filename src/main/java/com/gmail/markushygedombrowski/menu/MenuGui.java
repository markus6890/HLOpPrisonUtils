package com.gmail.markushygedombrowski.menu;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuGui implements Listener {
    private final int WARP_INDEX = 10;
    private final int INFO_INDEX = 22;

    public void create(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 45, p.getName() + " §9Menu");
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack warp = api.getItemHead("30032");
        ItemStack info = new ItemStack(Material.PLAYER_HEAD);


        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("§7Dine §9Stats");
        ItemMeta warpMeta = warp.getItemMeta();
        warpMeta.setDisplayName("§aWarps");
        List<String> lore = new ArrayList<>();
        lore.add(0, "§7Se alle §aWarps");
        warpMeta.setLore(lore);

        lore.set(0, "§7Klik for og se dine §9Stats");
        infoMeta.setLore(lore);
        info.setItemMeta(infoMeta);
        warp.setItemMeta(warpMeta);
        inventory.setItem(INFO_INDEX, info);
        inventory.setItem(WARP_INDEX, warp);
        p.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        InventoryView invv = event.getView();
        ItemStack clickeditem = event.getCurrentItem();
        int clickedSlot = event.getRawSlot();


        double pay;
        if (clickeditem == null) {
            return;
        }
        if (invv.getTitle().equalsIgnoreCase(p.getName() + " §9Menu")) {

            event.setCancelled(true);
            event.setResult(Event.Result.DENY);

        }
    }
}
