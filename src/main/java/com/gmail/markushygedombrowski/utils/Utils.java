package com.gmail.markushygedombrowski.utils;

import com.gmail.markushygedombrowski.OpPrisonUtils;
import com.gmail.markushygedombrowski.mines.MineInfo;
import com.gmail.markushygedombrowski.mines.MineManager;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    private static OpPrisonUtils plugin;

    public Utils(OpPrisonUtils plugin) {
        Utils.plugin = plugin;
    }

    public static boolean procent(double pro) {
        Random r = new Random();
        double num = r.nextInt(100);
        return num < pro;

    }
    public static boolean isNumeric(String string) {
        int intValue;
        if(string == null || string.equals("")) {

            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }





    public static boolean isItem(ItemStack item,String name ) {
        return item != null && item.getType() != Material.AIR && item.getItemMeta().getDisplayName().contains(name);
    }


    public static boolean isInRegion(org.bukkit.Location ploc, String name) {
        Location loc = BukkitAdapter.adapt(ploc);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(loc);
        for(ProtectedRegion region : set) {
            if(region.getId().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static String getRegions(Player p) {
        Location loc = BukkitAdapter.adapt(p.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(loc);
        return set.toString();
    }

    public static MineInfo getMine(org.bukkit.Location loc, MineManager mineManager) {
        Map<String, MineInfo> mineinfo = mineManager.getMineMap();
        MineInfo[] mine = new MineInfo[1];
        mineinfo.entrySet().stream().filter(entry -> {
            return Utils.isInRegion(loc,entry.getKey());
        }).forEach(entry -> mine[0] = entry.getValue());
        return mine[0];
    }

    public static List<String> convertStringToList(String st) {
        return Arrays.stream(st.split("\n")).toList();
    }





}
