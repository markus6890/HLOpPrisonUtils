package com.gmail.markushygedombrowski.sell;

import com.gmail.markushygedombrowski.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SellManager {
    private Map<String, InfoSell> sellList = new HashMap<>();
    private ConfigManager configM;

    public SellManager(ConfigManager configM) {
        this.configM = configM;
    }



    public void load() {
        FileConfiguration config = configM.getItems();
        sellList.clear();
        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            String item = config.getString("items." + key + ".name");
            int prize = config.getInt("items." + key + ".prize");
            Material material = Material.getMaterial(item);
            InfoSell infoSell = new InfoSell(material,prize);
            sellList.put(item,infoSell);
        }
    }

    public void save(InfoSell infoSell) {
        FileConfiguration config = configM.getItems();
        Material material = infoSell.getMaterial();
        String item = material.name();
        config.set("items." + infoSell.materialName() + ".name",item);
        config.set("items." + infoSell.materialName() + ".prize",infoSell.getPrize());
        configM.saveItems();
        sellList.put(infoSell.materialName(),infoSell);
    }
    public InfoSell getInfoSell(String item) {
        return sellList.get(item);
    }


}
