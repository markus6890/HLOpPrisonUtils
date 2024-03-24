package com.gmail.markushygedombrowski.listener;


import com.gmail.markushygedombrowski.mines.Mines;
import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {

    private Mines mines;
    public Listeners(Mines mines) {
        this.mines = mines;
    }

    @EventHandler
    public void dura(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (p.isOp()) return;
        ItemStack item = event.getItemDrop().getItemStack();
        if (Utils.isItem( item, "§bPickaxe") || Utils.isItem(item, "§9Menu")) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void itemMove(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (p.isOp()) return;
        if (Utils.isItem(item, "§bPickaxe") || Utils.isItem(item, "§9Menu")) {
            event.setCancelled(true);
            event.isCancelled();
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        Block block = event.getBlock();


        if(p.hasPermission("bygger")) return;

        if (Utils.isItem(item, "§bPickaxe")) {
            mines.mine(p, block);
        }
        event.setCancelled(true);

    }
}
