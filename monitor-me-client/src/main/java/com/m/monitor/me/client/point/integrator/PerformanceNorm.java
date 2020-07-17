package com.m.monitor.me.client.point.integrator;

import com.m.monitor.me.client.point.collector.MonitorPoint;

import java.util.concurrent.atomic.AtomicLong;

public class PerformanceNorm {
    private String name;
    private long minNorm=Long.MAX_VALUE;
    private long maxNorm=0L;
    private AtomicLong sumNorm= new AtomicLong(0);
    private AtomicLong total= new AtomicLong(0);

    public PerformanceNorm(String name) {
        this.name = name;
    }

    public PerformanceNorm() {

    }

    public void add(MonitorPoint point){
        long norm=point.getNorm();
        if (norm<minNorm){
            minNorm=norm;
        }
        if (norm>maxNorm){
            maxNorm=norm;
        }
        sumNorm.addAndGet(norm);
        total.incrementAndGet();
    }

    @Override
    public String toString() {
        return "["
                + minNorm +","
                + maxNorm +","
                + sumNorm +","
                + total +
                ']';
    }

    public String getName() {
        return name;
    }

    public long getMinNorm() {
        return minNorm;
    }

    public long getMaxNorm() {
        return maxNorm;
    }

    public AtomicLong getSumNorm() {
        return sumNorm;
    }

    public AtomicLong getTotal() {
        return total;
    }
}
