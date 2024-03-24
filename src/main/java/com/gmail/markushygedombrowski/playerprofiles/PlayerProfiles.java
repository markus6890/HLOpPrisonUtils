package com.gmail.markushygedombrowski.playerprofiles;

import com.gmail.markushygedombrowski.utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfiles {
    private final Map<UUID, PlayerInfo> profileMap = new HashMap<>();
    private final JavaPlugin plugin;
    private ConfigManager configM;

    public PlayerProfiles(JavaPlugin plugin, ConfigManager configM) {
        this.plugin = plugin;
        this.configM = configM;
    }

    public void load() {
        FileConfiguration config = configM.getProfiles();
        profileMap.clear();
        for(String uuidst : config.getConfigurationSection("players").getKeys(false)) {
            String name = config.getString("players." + uuidst + ".name");
            int gems = config.getInt("players." + uuidst + ".gems");
            int gold = config.getInt("players." + uuidst + ".gold");
            int picklvl = config.getInt("players." + uuidst + ".picklvl");
            long pickexp = config.getLong("players." + uuidst + ".pickexp");
            long pickexpneed = config.getLong("players." + uuidst + ".pickexpneed");
            long money = config.getLong("players." + uuidst + ".money");
            UUID uuid = UUID.fromString(uuidst);
            PlayerInfo playerInfo = new PlayerInfo(gems,uuid,name,gold,pickexp,picklvl,pickexpneed,money);
            profileMap.put(uuid,playerInfo);
        }
    }
    public void save(PlayerInfo playerInfo) {
        FileConfiguration config = configM.getProfiles();
        String uuidst = playerInfo.getUuid().toString();
        config.set("players." + uuidst + ".name",playerInfo.getName());
        config.set("players." + uuidst + ".gems", playerInfo.getGems());
        config.set("players." + uuidst + ".gold",playerInfo.getGold());
        config.set("players." + uuidst + ".picklvl", playerInfo.getPicklvl());
        config.set("players." + uuidst + ".pickexp", playerInfo.getPickexp());
        config.set("players." + uuidst + ".pickexpneed",playerInfo.getPickexpneed());
        config.set("players." + uuidst + ".money", playerInfo.getMoney());
        configM.saveProfiles();
        profileMap.put(playerInfo.getUuid(),playerInfo);
    }
    public PlayerInfo getPlayerinfo(UUID uuid) {
        return profileMap.get(uuid);
    }

}
