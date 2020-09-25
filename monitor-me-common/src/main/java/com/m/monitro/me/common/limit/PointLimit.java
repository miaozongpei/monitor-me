package com.m.monitro.me.common.limit;
public class PointLimit {
    private int waitingThreadMax=Integer.MAX_VALUE;
    private boolean isBreak=false;
    private int tpsMax=Integer.MAX_VALUE;
    private int sleepMillis=0;



    private int currentTps;

    public int getWaitingThreadMax() {
        return waitingThreadMax;
    }

    public void setWaitingThreadMax(int waitingThreadMax) {
        this.waitingThreadMax = waitingThreadMax;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }

    public int getTpsMax() {
        return tpsMax;
    }

    public void setTpsMax(int tpsMax) {
        this.tpsMax = tpsMax;
    }

    public int getSleepMillis() {
        return sleepMillis;
    }

    public void setSleepMillis(int sleepMillis) {
        this.sleepMillis = sleepMillis;
    }

    public int getCurrentTps() {
        return currentTps;
    }

    public void setCurrentTps(int currentTps) {
        this.currentTps = currentTps;
    }
}
