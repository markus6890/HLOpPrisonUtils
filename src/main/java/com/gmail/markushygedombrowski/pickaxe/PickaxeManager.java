package com.gmail.markushygedombrowski.pickaxe;

import com.gmail.markushygedombrowski.mines.MineInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import com.gmail.markushygedombrowski.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PickaxeManager implements Listener {
    private final static int PICKAXE_SLOT = 0;
    private final static int MENU_SLOT = 8;
    private final PlayerProfiles playerProfiles;
    private final PickaxeLoader pickaxeLoader;

    public PickaxeManager(PlayerProfiles playerProfiles, PickaxeLoader pickaxeLoader) {
        this.playerProfiles = playerProfiles;
        this.pickaxeLoader = pickaxeLoader;
    }


    public void pickAxe(Player p, MineInfo mineInfo) {

        PlayerInfo playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());
        ItemStack item = p.getInventory().getItemInMainHand();


        BaseComponent text = new TextComponent("Pickaxe Exp: " + playerInfo.getPickexp() + "/" + playerInfo.getPickexpneed());
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);

        playerInfo.setPickexp(playerInfo.getPickexp() + mineInfo.getExpGain());

        if (!(playerInfo.getPickexp() >= playerInfo.getPickexpneed())) return;
        int lvl = playerInfo.getPicklvl() + 1;
        lvlUpPickaxe(item, playerInfo, p, lvl);
    }

    public void givePermanentItems(Player p, PlayerInfo playerInfo) {


        ItemStack item = p.getInventory().getItem(PICKAXE_SLOT);
        Pickaxe pickaxe = pickaxeLoader.getPickaxe(playerInfo.getPicklvl());
        if(!pickaxe.getPickaxeItem(playerInfo).isSimilar(item)) {
            giveNewPickAxe(p,playerInfo,pickaxe);
        }

        item = p.getInventory().getItem(MENU_SLOT);
        if (!Utils.isItem(item, "§9Menu")) {
            giveNewMenu(p);
        }


    }

    public void giveNewMenu(Player p) {
        ItemStack menu = new ItemStack(Material.NETHER_STAR);
        ItemMeta menuMeta = menu.getItemMeta();

        itemDisplayName(menuMeta, "§9Menu", menu);
        p.getInventory().setItem(8, menu);
    }



    public void giveNewPickAxe(Player p, PlayerInfo playerInfo, Pickaxe pickaxe) {

        p.getInventory().setItem(PICKAXE_SLOT, pickaxe.getPickaxeItem(playerInfo));
    }


    private void itemDisplayName(ItemMeta meta, String displayName, ItemStack item) {
        assert meta != null;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }


    public void lvlUpPickaxe(ItemStack item, PlayerInfo playerInfo, Player p, int lvl) {

        setPickaxeLvlAndExpNeed(playerInfo, lvl);

        pickaxeMaterial(item, playerInfo);

        setPickAxeDisplayName(item, playerInfo, p);

        playerProfiles.save(playerInfo);
    }

    public void setPickAxeDisplayName(ItemStack item, PlayerInfo playerInfo, Player p) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bPickaxe §fLvl " + playerInfo.getPicklvl());
        item.setItemMeta(meta);
        p.sendMessage("[§9§LPickaxe§f] Din §bPickaxe er nu i level §a" + playerInfo.getPicklvl());
    }

    public void setPickaxeLvlAndExpNeed(PlayerInfo playerInfo, int lvl) {

        playerInfo.setPicklvl(lvl);
        playerInfo.setPickexp(0);
        playerInfo.setPickexpneed(playerInfo.getPickexpneed() * playerInfo.getPicklvl());
        if (playerInfo.getPicklvl() == 1) {
            playerInfo.setPickexpneed(200);
        }
    }


    public void pickaxeMaterial(ItemStack item, PlayerInfo playerInfo) {
        if (playerInfo.getPicklvl() >= 100) {
            item.setType(Material.NETHERITE_PICKAXE);
        } else if (playerInfo.getPicklvl() >= 75) {
            item.setType(Material.DIAMOND_PICKAXE);
        } else if (playerInfo.getPicklvl() >= 50) {
            item.setType(Material.GOLDEN_PICKAXE);
        } else if (playerInfo.getPicklvl() >= 25) {
            item.setType(Material.IRON_PICKAXE);
        } else if (playerInfo.getPicklvl() >= 10) {
            item.setType(Material.STONE_PICKAXE);
        } else {
            item.setType(Material.WOODEN_PICKAXE);
        }
    }


}