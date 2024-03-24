package com.gmail.markushygedombrowski.gems;

import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Gems {
    private Map<UUID, Integer> gemTime = new HashMap<>();

    private PlayerProfiles playerProfiles;

    public Gems(PlayerProfiles playerProfiles) {
        this.playerProfiles = playerProfiles;
    }

    public void addToCoolDown(Player p) {
        int gemtime = 15600;
        if (p.hasPermission("legend")) {
            gemtime = 12000;
        }
        gemTime.put(p.getUniqueId(), gemtime);
    }




    public void gemCoolDown() {
        gemTime.entrySet().forEach(entry -> {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null || p.getGameMode() != GameMode.SURVIVAL) return;

            if (!(entry.getValue() <= 0)) {
                entry.setValue(entry.getValue() - 1);
                return;
            }
            giveGems(p, 1);
            entry.setValue(resetCoolDownTime(p));

        });
    }

    private int resetCoolDownTime(Player p) {
        int time  = 15600;
        if (p.hasPermission("legend")) time = 12000;
        return  time;
    }

    public void giveGems(Player p, int gems) {
        PlayerInfo info = playerProfiles.getPlayerinfo(p.getUniqueId());
        if (gems == 0) return;
        String stgem = " §aGem";
        if (gems > 1) stgem = " §aGems";
        info.setGems(info.getGems() + gems);
        p.sendMessage("Du har fået " + gems + stgem);

    }

    public void removeFromCoolDown(Player p) {
        gemTime.remove(p.getUniqueId());
    }
}
