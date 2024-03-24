package com.gmail.markushygedombrowski.mines;

import java.util.List;

public class MineInfo {
    private String name;
    private List<String> blocks;
    private int expGain;

    public MineInfo(String name, List<String> blocks,int expGain) {
        this.name = name;
        this.blocks = blocks;
        this.expGain = expGain;
    }

    public int getExpGain() {
        return expGain;
    }

    public void setExpGain(int expGain) {
        this.expGain = expGain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }

    public boolean isBlockIn(String block) {
        for(String st : blocks) {
            if(st == null) return false;
            if(st.equals(block)) {
                return true;
            }
        }
        return false;
    }
}
