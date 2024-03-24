package com.gmail.markushygedombrowski.sell;


import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class SellCommand implements CommandExecutor {
    private SellManager sellManager;
    private PlayerProfiles playerProfiles;

    public SellCommand(SellManager sellManager, PlayerProfiles playerProfiles) {
        this.sellManager = sellManager;
        this.playerProfiles = playerProfiles;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("kan kun bruges InGame");
            return true;
        }
        Player p = (Player) sender;
        PlayerInfo playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());

        if(alias.equalsIgnoreCase("addsell")) {
            addSell(p,args);
            return true;
        }

        long[] money = new long[1];
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                InfoSell infoSell = sellManager.getInfoSell(item.getType().name());
                p.sendMessage(item.getType().name());
                if (infoSell != null) {
                    money[0] = money[0] + (long) infoSell.getPrize() * item.getAmount();
                    p.getInventory().removeItem(item);
                }
            }
        }
        if (money[0] == 0) return true;
        playerInfo.addMoney(money[0]);
        p.sendMessage("Du har solgt for: §a" + money[0]);
        playerProfiles.save(playerInfo);


        return true;
    }


    public void addSell(Player p, String[] args) {
        if(!p.hasPermission("admin")) {
            p.sendMessage("§cDet har du ikke permission til!");
            return;
        }
        if(args.length == 0) {
            p.sendMessage("/addsell <prize>");
            return;
        }
        if(!Utils.isNumeric(args[0])) {
            return;
        }

        if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            p.sendMessage("Tag en item i hånden");
            return;
        }

        int prize = Integer.parseInt(args[0]);
        Material item = p.getInventory().getItemInMainHand().getType();
        InfoSell infoSell = sellManager.getInfoSell(item.name());
        if (infoSell != null) {
            p.sendMessage("Det er allerede Blocked");
            return;
        }
        p.sendMessage("Sell item added");
        infoSell = new InfoSell(item,prize);
        sellManager.save(infoSell);


    }
}
