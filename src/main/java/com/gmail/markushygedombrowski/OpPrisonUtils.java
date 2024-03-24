package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.commands.AdminCommand;
import com.gmail.markushygedombrowski.commands.BalCommand;
import com.gmail.markushygedombrowski.commands.GamemodeCommand;
import com.gmail.markushygedombrowski.commands.TpCommands;
import com.gmail.markushygedombrowski.gems.Gems;
import com.gmail.markushygedombrowski.listener.JoinLeave;
import com.gmail.markushygedombrowski.listener.Listeners;
import com.gmail.markushygedombrowski.menu.MenuGui;
import com.gmail.markushygedombrowski.menu.MenuOpen;
import com.gmail.markushygedombrowski.mines.CreateMine;
import com.gmail.markushygedombrowski.mines.MineManager;
import com.gmail.markushygedombrowski.pickaxe.PickaxeLoader;
import com.gmail.markushygedombrowski.pickaxe.PickaxeManager;
import com.gmail.markushygedombrowski.mines.Mines;
import com.gmail.markushygedombrowski.playerprofiles.PlayerProfiles;
import com.gmail.markushygedombrowski.sell.SellCommand;
import com.gmail.markushygedombrowski.sell.SellManager;
import com.gmail.markushygedombrowski.utils.ConfigManager;
import com.gmail.markushygedombrowski.utils.Configreloadcommand;
import com.gmail.markushygedombrowski.utils.Utils;
import com.gmail.markushygedombrowski.warp.WarpCommand;
import com.gmail.markushygedombrowski.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class OpPrisonUtils extends JavaPlugin {

    private ConfigManager configM;
    private WarpManager warpManager;
    private PlayerProfiles playerProfiles;
    private PickaxeManager pickaxeManager;
    private SellManager sellManager;
    private MineManager mineManager;
    private Mines mines;
    private PickaxeLoader pickaxeLoader;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigManager();
        playerProfiles = new PlayerProfiles(this,configM);
        mineManager = new MineManager(configM);
        sellManager = new SellManager(configM);
        mineManager.load();
        playerProfiles.load();
        Utils utils = new Utils(this);
        sellManager.load();
        initWarps();

        Gems gems = new Gems(playerProfiles);
        MenuGui menuGui = new MenuGui();
        Bukkit.getPluginManager().registerEvents(menuGui,this);


        MenuOpen menuOpen = new MenuOpen(menuGui);
        Bukkit.getPluginManager().registerEvents(menuOpen,this);

        pickaxeLoader = new PickaxeLoader();
        pickaxeLoader.load(getConfig());



        pickaxeManager = new PickaxeManager(playerProfiles, pickaxeLoader);
        Bukkit.getPluginManager().registerEvents(pickaxeManager,this);


        JoinLeave joinLeave = new JoinLeave(playerProfiles, pickaxeManager,gems);
        Bukkit.getPluginManager().registerEvents(joinLeave,this);

        mines = new Mines(mineManager,this, pickaxeManager);

        Listeners listeners = new Listeners(mines);
        Bukkit.getPluginManager().registerEvents(listeners,this);

        initCommands();




        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                mines.blockCooldown();
                gems.gemCoolDown();
            }
        }, 1L, 1L);
    }

    public void loadConfigManager() {
        configM = new ConfigManager();
        configM.setup();
        configM.saveWarps();
        configM.saveItems();
        configM.saveKits();
        configM.saveProfiles();
        configM.reloadItems();
        configM.reloadWarps();
        configM.reloadKits();
        configM.reloadProfiles();
        configM.saveMines();
        configM.reloadMines();

    }

    public void reload() {
        reloadConfig();
        FileConfiguration config = getConfig();
        loadConfigManager();
        warpManager.load();
        playerProfiles.load();
        sellManager.load();
        mineManager.load();
        pickaxeLoader.load(config);


    }

    private void initWarps() {
        warpManager = new WarpManager(this, configM);
        warpManager.load();
        WarpCommand warpCommand = new WarpCommand(warpManager, this);
        getCommand("warp").setExecutor(warpCommand);
    }
    private void initCommands() {
        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        getCommand("gamemode").setExecutor(gamemodeCommand);

        TpCommands tpCommands = new TpCommands();
        getCommand("tp").setExecutor(tpCommands);

        Configreloadcommand configreloadcommand = new Configreloadcommand(this);
        getCommand("utilsreload").setExecutor(configreloadcommand);

        AdminCommand adminCommand = new AdminCommand(playerProfiles, pickaxeManager);
        getCommand("adminpickaxe").setExecutor(adminCommand);

        SellCommand sellCommand = new SellCommand(sellManager,playerProfiles);
        getCommand("sell").setExecutor(sellCommand);

        BalCommand balCommand = new BalCommand(playerProfiles);
        getCommand("bal").setExecutor(balCommand);

        CreateMine createMine = new CreateMine(mineManager);
        getCommand("createmine").setExecutor(createMine);
    }



    @Override
    public void onDisable() {

    }

}
