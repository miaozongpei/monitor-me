package com.m.monitro.me.common.limit;

import java.util.concurrent.atomic.AtomicInteger;

public class PointLimit {
    private int waitingThreadMax=9999;
    private int breakFlag=0;
    private int tpsMax=9999;
    private int sleepMillis=0;


    private AtomicInteger currentTps;

    public int getWaitingThreadMax() {
        return waitingThreadMax;
    }

    public void setWaitingThreadMax(int waitingThreadMax) {
        this.waitingThreadMax = waitingThreadMax;
    }

    public int getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(int breakFlag) {
        this.breakFlag = breakFlag;
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

    public  AtomicInteger getCurrentTps() {
        if (currentTps==null){
            currentTps=new AtomicInteger();
        }
        return currentTps;
    }

    @Override
    public String toString() {
        return "maxWaiting=" + waitingThreadMax +
                ", maxTps=" + tpsMax +
                ", sleep=" + sleepMillis +
                ", isBreak=" + (breakFlag!=0);
    }
}
