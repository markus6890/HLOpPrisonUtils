package com.gmail.markushygedombrowski.mines;

import com.gmail.markushygedombrowski.OpPrisonUtils;
import com.gmail.markushygedombrowski.pickaxe.PickaxeManager;
import com.gmail.markushygedombrowski.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Mines {
    private MineManager mineManager;
    private OpPrisonUtils plugin;
    private PickaxeManager pickaxeManager;
    private Map<Block, Integer> blockTime = new HashMap<>();


    public Mines(MineManager mineManager, OpPrisonUtils plugin, PickaxeManager pickaxeManager) {
        this.mineManager = mineManager;
        this.plugin = plugin;
        this.pickaxeManager = pickaxeManager;
    }


    public void mine(Player p, Block block) {

        Map<String, MineInfo> mineinfo = mineManager.getMineMap();
        mineinfo.entrySet().forEach(entry -> {
            if (!checkBlockLoc(block, entry)) return;

            MineInfo mine = mineManager.getMineInfo(entry.getKey());

            canMineBlock(p, block, mine);
        });

    }

    private void canMineBlock(Player p, Block block, MineInfo mine) {

        if (!mine.isBlockIn(block.getType().name())) return;

        Collection<ItemStack> drops = block.getDrops();

        World world = block.getWorld();
        drops.forEach(ent -> {

            ItemStack drop = new ItemStack(ent.getType());
            world.dropItem(block.getLocation(), drop);

        });


        pickaxeManager.pickAxe(p, mine);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                block.setType(Material.BEDROCK);
            }
        }, 2);
        blockTime.put(block, 40);

    }


    private boolean checkBlockLoc(Block block, Map.Entry<String, MineInfo> entry) {
        return Utils.isInRegion(block.getLocation(), entry.getKey());

    }

    public void blockCooldown() {
        if (blockTime == null) return;


        new HashSet<>(blockTime.entrySet()).forEach(entry -> {
            if (!(entry.getValue() == 0)) {
                entry.setValue(entry.getValue() - 1);
                return;
            }

            Block block = entry.getKey();
            
            MineInfo mineInfo = Utils.getMine(block.getLocation(), mineManager);
            List<String> mat = mineInfo.getBlocks();
            if (mat == null) return;

            Random rand = new Random();
            String rBlock = mat.get(rand.nextInt(mat.size()));
            Material replace = Material.getMaterial(rBlock);
            if (replace == null) {
                replace = Material.STONE;
            }
            block.setType(replace);
            blockTime.remove(entry.getKey());



        });
    }
}
