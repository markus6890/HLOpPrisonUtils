package com.gmail.markushygedombrowski.playerprofiles;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInfo {
    private int gems;
    private UUID uuid;
    private String name;
    private long money;



    private int gold;
    private int picklvl;
    private long pickexp;
    private long pickexpneed;

    public PlayerInfo (Player p) {
        this(1,p.getUniqueId(),p.getName(),1,1,1,200,1);
    }



    public PlayerInfo(int gems, UUID uuid, String name, int gold, long pickexp, int picklvl, long pickexpneed,long money) {
        this.gems = gems;
        this.uuid = uuid;
        this.name = name;
        this.gold = gold;
        this.pickexp = pickexp;
        this.picklvl = picklvl;
        this.pickexpneed = pickexpneed;
        this.money = money;
    }

    public int getGems() {
        return gems;
    }
    public UUID getUuid() {
        return uuid;
    }
    public int getPicklvl() {
        return picklvl;
    }
    public String getName() {
        return name;
    }
    public int getGold() {
        return gold;
    }
    public long getPickexp() {
        return pickexp;
    }
    public long getPickexpneed() {
        return pickexpneed;
    }
    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
    public void setPickexpneed(long pickexpneed) {
        this.pickexpneed = pickexpneed;
    }
    public void setGems(int gems) {
        this.gems = gems;
    }
    public void setPicklvl(int picklvl) {
        this.picklvl = picklvl;
    }
    public void setPickexp(long pickexp) {
        this.pickexp = pickexp;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }


    public void addMoney(long amount) {
        this.money = this.money + amount;
    }
    public void addGold(int amount) {
        this.gold = gold + amount;
    }

    public void removeGems(int amount) {
        this.gems = this.gems - amount;
    }
    public void removeMoney(long amount) {
        this.money = this.money - amount;
    }
    public void removeGold(int amount) {
        this.gold = gold - amount;
    }

    public boolean hasMoney(long amount) {
        return getMoney() >= amount;
    }
    public boolean hasGems(int amount) {
        return getGems() >= amount;
    }
    public boolean hasGold(int amount) {
        return getGold() >= amount;
    }

}