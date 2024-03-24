package com.gmail.markushygedombrowski.sell;

import org.bukkit.Material;

public class InfoSell {

    private Material material;
    private int prize;



    public InfoSell(Material material, int prize) {
        this.material = material;
        this.prize = prize;
    }

    public Material getMaterial() {
        return material;
    }
    public String materialName() {
        return material.name();
    }

    public int getPrize() {
        return prize;
    }
    public void setPrize(int prize) {
        this.prize = prize;
    }
}
