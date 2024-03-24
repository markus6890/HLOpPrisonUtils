package com.gmail.markushygedombrowski.listener;

import com.gmail.markushygedombrowski.gems.Gems;
import com.gmail.markushygedombrowski.pickaxe.PickaxeManager;
import com.gmail.markushygedombrowski.playerprofiles.PlayerInfo;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener {

    private final PlayerProfiles playerProfiles;
    private final PickaxeManager pickaxeManager;
    private final Gems gems;


    public JoinLeave(PlayerProfiles playerProfiles, PickaxeManager pickaxeManager, Gems gems) {
        this.playerProfiles = playerProfiles;
        this.pickaxeManager = pickaxeManager;
        this.gems = gems;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        PlayerInfo playerInfo = playerProfiles.getPlayerinfo(p.getUniqueId());
        newPlayer(p, playerInfo);

        gems.addToCoolDown(p);
        pickaxeManager.givePermanentItems(p, playerInfo);

    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        PlayerInfo info = playerProfiles.getPlayerinfo(p.getUniqueId());
        playerProfiles.save(info);
        gems.removeFromCoolDown(p);
    }


    public void newPlayer(Player p, PlayerInfo playerInfo) {
        if (playerInfo != null) {
            return;
        }

        playerInfo = new PlayerInfo(p);
        playerProfiles.save(playerInfo);

    }
}
