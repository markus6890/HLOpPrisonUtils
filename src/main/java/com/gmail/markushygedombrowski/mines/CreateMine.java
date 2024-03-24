package com.gmail.markushygedombrowski.mines;

import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateMine implements CommandExecutor {
    private MineManager mineManager;

    public CreateMine(MineManager mineManager) {
        this.mineManager = mineManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("§cKan kun bruges InGame!");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("createMine")) {
            p.sendMessage("§cDet har du ikke permission til!");
            return true;
        }

        if (args.length <= 1) {
            p.sendMessage("/createmine <regionname> <expgain>");
            p.sendMessage("/replacemine <regionname>, <expgain>");

            return true;
        }
        String mine = args[0];
        String expgain = args[1];
        MineInfo mineInfo = mineManager.getMineInfo(mine);
        if(alias.equalsIgnoreCase("replacemine")) {
            replaceMine(mineInfo,p,expgain);
            return true;

        }

        if(expgain == null) expgain = "1";
        if (!Utils.isNumeric(expgain)) return true;
        createMine(mine,p,Integer.parseInt(expgain),mineInfo);
        return true;
    }




    public void createMine(String mine, Player p, int expgain, MineInfo mineInfo) {
        if (mineInfo != null) {
            p.sendMessage("Det mine er allerde lavet");
            return;
        }

        List<String> saveblocks = new ArrayList<>();
        ItemStack[] blocks = p.getInventory().getContents();
        for (ItemStack block : blocks) {
            if (!(block == null)) if (block.getType().isBlock()) {
                saveblocks.add(block.getType().name());
            }
        }

        if(saveblocks.isEmpty()) {
            p.sendMessage("§7Du har §cikke §7nogen §ablocks §7på dig");
            return;
        }
        mineInfo = new MineInfo(mine,saveblocks,expgain);
        p.sendMessage("§bMine §aCreated");
        p.sendMessage("§bMine §7" + mine);
        mineManager.save(mineInfo);
    }

    public void replaceMine(MineInfo mineInfo, Player p,String expgain) {
        if(mineInfo == null) {
            p.sendMessage("Denne mine findes ikke!");
            return;
        }

        if(expgain == null) expgain = ""+mineInfo.getExpGain();
        if (!Utils.isNumeric(expgain)) return;

        List<String> saveblocks;
        List<ItemStack> blocks = Arrays.stream(p.getInventory().getContents()).toList();

        saveblocks = blocks.stream().filter(entry -> {
            return !(entry == null) && entry.getType().isBlock();
        }).map(entry -> entry.getType().name()).collect(Collectors.toList());


        if(saveblocks.isEmpty()) {
            p.sendMessage("§7Du har §cikke §7nogen §ablocks §7på dig");
            return;
        }

        mineInfo.setBlocks(saveblocks);
        p.sendMessage("§bMine §aBlocks replaced");
        p.sendMessage("§bMine §7" + mineInfo.getName());
    }
}
