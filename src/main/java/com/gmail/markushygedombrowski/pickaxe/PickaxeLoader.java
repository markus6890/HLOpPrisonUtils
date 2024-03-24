package com.gmail.markushygedombrowski.pickaxe;

import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PickaxeLoader {
    private final Map<Integer, Pickaxe> pickaxeMap = new HashMap<>();

    public void load(FileConfiguration config) {
        pickaxeMap.clear();
        config.getConfigurationSection("pickaxe").getKeys(false).forEach(entry -> {
            if (!Utils.isNumeric(entry)) {
                System.out.println("is not Integer: " + entry);
                return;
            }
            String path = "pickaxe." + entry + ".";
            String name = config.getString(path + "name");
            String lore = config.getString(path + "lore");
            String materialString = config.getString(path + "material");
            int digSpeed = config.getInt(path + "digSpeed");

            Material material = Material.matchMaterial(materialString);
            Pickaxe pickaxe = new Pickaxe(name, lore, material, digSpeed);

            int level = Integer.parseInt(entry);
            pickaxeMap.put(level, pickaxe);
        });
    }

    public Pickaxe getPickaxe(int level) {
        Integer pickaxeLvl = pickaxeMap.keySet().stream()
                .filter(entry -> level >= entry)
                .max((o1,o2) -> o1.compareTo(o2)).orElse(null);
        if (pickaxeLvl == null) {
            return null;
        }

        return pickaxeMap.get(pickaxeLvl);

    }

}
