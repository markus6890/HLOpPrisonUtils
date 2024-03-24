package com.gmail.markushygedombrowski.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        Location loc;
        Player p1;
        Player p2;
        if (!sender.hasPermission("Teleport")) {
            sender.sendMessage("§cdet har du ikke permission til!!");
            return true;
        }


        if (args.length == 0) {
            sender.sendMessage("/tp <player> \"to\" <player>");

        }
        if (alias.equalsIgnoreCase("tphere")) {
            p1 = (Player) sender;
            p2 = Bukkit.getPlayer(args[0]);
            loc = p1.getLocation();

            if (p2 == null) return true;
            p2.teleport(loc);
            return true;

        }

        if (args.length == 1) {
            tpSenderToPlayer(sender,args);
            return true;
        }
        if (args.length == 2) tpArg1ToArg2(args);


        return true;
    }



    private void tpArg1ToArg2(String[] args) {
        Player p2;
        Location loc;
        Player p1;
        p1 = Bukkit.getPlayer(args[0]);
        p2 = Bukkit.getPlayer(args[1]);
        if (p1 == null || p2 == null) return;
        loc = p2.getLocation();
        p1.teleport(loc);

    }

    private void tpSenderToPlayer(CommandSender sender, String[] args) {
        Player p1;
        Player p2;
        Location loc;
        if (!(sender instanceof Player)) {
            System.out.println("kan kun bruges af players");
            return;
        }
        p1 = (Player) sender;
        p2 = Bukkit.getPlayer(args[0]);
        if (p2 == null) return;
        loc = p2.getLocation();

        p1.teleport(loc);


    }
}
