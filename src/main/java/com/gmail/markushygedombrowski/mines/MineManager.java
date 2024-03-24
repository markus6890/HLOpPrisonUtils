package com.gmail.markushygedombrowski.mines;

import com.gmail.markushygedombrowski.utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineManager {
    private ConfigManager configM;
    private Map<String,MineInfo> mineMap = new HashMap<>();

    public MineManager(ConfigManager configM) {
        this.configM = configM;
    }
    public void load() {
        FileConfiguration config = configM.getMines();
        mineMap.clear();
        for(String mine : config.getConfigurationSection("mines").getKeys(false)) {
            String name = config.getString("mines." + mine + ".name");
            List<String> blocks = ((List<String>) config.get("mines." + mine + ".blocks"));
            int expGain = config.getInt("mines." + mine +".expgain");
            MineInfo mineInfo = new MineInfo(name,blocks,expGain);
            mineMap.put(name,mineInfo);
        }
    }
    public void save(MineInfo mineInfo) {
        FileConfiguration config = configM.getMines();
        String mine = "mines." +mineInfo.getName();
        config.set(mine +".name", mineInfo.getName());
        config.set(mine +".blocks",mineInfo.getBlocks());
        config.set(mine + ".expgain",mineInfo.getExpGain());
        configM.saveMines();
        mineMap.put(mineInfo.getName(),mineInfo);
    }
    public MineInfo getMineInfo(String mine) {
        return mineMap.get(mine);
    }
    public Map<String, MineInfo> getMineMap() {
        return mineMap;
    }
}
