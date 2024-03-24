package com.gmail.markushygedombrowski.utils;

import com.gmail.markushygedombrowski.OpPrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private OpPrisonUtils plugin = OpPrisonUtils.getPlugin(OpPrisonUtils.class);

    public FileConfiguration warpscfg;
    public File warpsFile;
    public FileConfiguration itemcfg;
    public File itemFile;
    public FileConfiguration kitscfg;
    public File kitsFile;
    public FileConfiguration profilescfg;
    public File profileFile;
    public FileConfiguration minecfg;
    public File mineFile;

    public void setup() {
        List<File> configList = new ArrayList<>();
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();

        }
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        itemFile = new File(plugin.getDataFolder(),"sell.yml");
        kitsFile = new File(plugin.getDataFolder(),"kits.yml");
        profileFile = new File(plugin.getDataFolder(),"players.yml");
        mineFile = new File(plugin.getDataFolder(),"mines.yml");
        configList.add(warpsFile);
        configList.add(itemFile);
        configList.add(kitsFile);
        configList.add(profileFile);
        configList.add(mineFile);

        configList.forEach(file -> {
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not create " + file.getName() + "File");
                }
            }
        });
        itemcfg = YamlConfiguration.loadConfiguration(itemFile);
        warpscfg = YamlConfiguration.loadConfiguration(warpsFile);
        kitscfg = YamlConfiguration.loadConfiguration(kitsFile);
        profilescfg = YamlConfiguration.loadConfiguration(profileFile);
        minecfg = YamlConfiguration.loadConfiguration(mineFile);

    }

    public FileConfiguration getWarps() {
        return warpscfg;
    }

    public FileConfiguration getItems() {
        return itemcfg;
    }

    public FileConfiguration getKits() {
        return kitscfg;
    }
    public FileConfiguration getProfiles(){
        return profilescfg;
    }
    public FileConfiguration getMines() {
        return minecfg;
    }


    public void saveWarps () {
        try {
            warpscfg.save(warpsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save warps.yml File");
        }
    }

    public void saveItems () {
        try {
            itemcfg.save(itemFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save sell.yml File");
        }
    }
    public void saveKits () {
        try {
            kitscfg.save(kitsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save kits.yml File");
        }
    }
    public void saveProfiles() {
        try {
            profilescfg.save(profileFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save players.yml File");
        }
    }
    public void saveMines() {
        try {
            minecfg.save(mineFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "could not save mines.yml File");
        }
    }


    public void reloadWarps() {
        warpscfg = YamlConfiguration.loadConfiguration(warpsFile);
    }
    public void reloadItems() {
        itemcfg = YamlConfiguration.loadConfiguration(itemFile);
    }
    public void reloadKits() {
        kitscfg = YamlConfiguration.loadConfiguration(kitsFile);
    }
    public void reloadProfiles() {
        profilescfg = YamlConfiguration.loadConfiguration(profileFile);
    }
    public void reloadMines() {
        minecfg = YamlConfiguration.loadConfiguration(mineFile);
    }
}
