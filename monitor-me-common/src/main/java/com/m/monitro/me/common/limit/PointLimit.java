package com.m.monitro.me.common.limit;
public class PointLimit {
    private int threadMax=-1;
    private boolean isBreak=false;
    private int tpsMax=Integer.MAX_VALUE;

    private int currentTps;
    public int getThreadMax() {
        return threadMax;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public int getTpsMax() {
        return tpsMax;
    }

    public int getCurrentTps() {
        return currentTps;
    }

    public void setCurrentTps(int currentTps) {
        this.currentTps = currentTps;
    }
}
