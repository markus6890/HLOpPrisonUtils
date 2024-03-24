package com.gmail.markushygedombrowski.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission("ban")) {
            sender.sendMessage("§cDet har du ikke permission til!");
            return true;
        }
        String reason;
        String playername;

        if(args.length <= 1) {
            sender.sendMessage("/ban <player> <reason> <time>");
            return true;
        }
        if(args.length == 2) {

        }
        playername = args[0];
        reason = args[1];
        Player p = Bukkit.getPlayer(playername);
        if(p == null){

            sender.sendMessage("denne spiller findes ikke");
            return true;
        }
        if(p.isBanned()) {
            sender.sendMessage("§c" + p.getName() + " §7er allerede banned");
            return true;
        }

        return true;
    }
}
