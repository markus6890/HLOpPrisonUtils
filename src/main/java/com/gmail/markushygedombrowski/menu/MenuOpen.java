package com.gmail.markushygedombrowski.menu;

import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;


public class MenuOpen implements Listener {
private MenuGui menuGui;

    public MenuOpen(MenuGui menuGui) {
        this.menuGui = menuGui;
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(event.getAction() == Action.RIGHT_CLICK_AIR ||event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Utils.isItem(item,"§9Menu")) {
                menuGui.create(p);
            }
        }
    }
}
