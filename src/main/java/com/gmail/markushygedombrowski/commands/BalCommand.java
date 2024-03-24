package com.gmail.markushygedombrowski.commands;

import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalCommand implements CommandExecutor {
    private PlayerProfiles playerProfiles;

    public BalCommand(PlayerProfiles playerProfiles) {
        this.playerProfiles = playerProfiles;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        PlayerInfo playerInfo;
        if(!(sender instanceof Player)) {
            System.out.println("Kan kun bruge InGame!");
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());
            sendBalText(sender, playerInfo, p,false);

        } else if(args.length == 1) {
            if(!p.hasPermission("admin")) return true;
            p = Bukkit.getPlayer(args[0]);

            if(p == null) {
                sender.sendMessage("Denne spiller findes ikke");
                return true;
            }

            playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());
            sendBalText(sender, playerInfo, p,true);
        }
        return true;
    }

    private  void sendBalText(CommandSender sender, PlayerInfo playerInfo, Player p, boolean admin) {
        String name = "Dine";
        if(admin) name = p.getName();
        sender.sendMessage("§f=========§9Info§f=========");
        sender.sendMessage("§e" + name + " §bpenge§7: §b" + playerInfo.getMoney());
        sender.sendMessage("§e" + name + " §aGems§7: §a" + playerInfo.getGems());
        sender.sendMessage("§e" + name + " §6Gold§7: §6" + playerInfo.getGold());
        sender.sendMessage("§f=========§9Info§f=========");
    }
}
