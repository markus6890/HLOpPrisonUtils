package com.gmail.markushygedombrowski.commands;

import com.gmail.markushygedombrowski.pickaxe.PickaxeManager;
import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminCommand implements CommandExecutor {
    private PlayerProfiles playerProfiles;
    private PickaxeManager pickaxeManager;

    public AdminCommand(PlayerProfiles playerProfiles, PickaxeManager pickaxeManager) {
        this.playerProfiles = playerProfiles;
        this.pickaxeManager = pickaxeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println("Kan kun bruge InGame!");
            return true;
        }
        if(!sender.hasPermission("admin")) return true;

        if(args.length == 0) {
            sender.sendMessage("/adminpickaxe <lvl> <player>");
            return true;
        }
        if(!Utils.isNumeric(args[0])){
            sender.sendMessage("er ikke Tal!!");
            return true;
        }
        int lvl = Integer.parseInt(args[0]);
        Player p = (Player) sender;
        if(args.length == 2) {
            if(Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage("§cDenne spiller findes ikke");
                return true;
            }
            p = Bukkit.getPlayer(args[1]);
        }
        PlayerInfo playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());
        ItemStack item = p.getInventory().getItemInMainHand();
        if(Utils.isItem(item,"§bPickaxe") || p.isOp()) {
            pickaxeManager.lvlUpPickaxe(item,playerInfo,p,lvl);
        }





        return true;
    }
}
