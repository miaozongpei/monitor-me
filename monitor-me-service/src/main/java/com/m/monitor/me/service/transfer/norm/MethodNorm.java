package com.m.monitor.me.service.transfer.norm;

import com.m.monitro.me.common.utils.DoubleUtil;
import lombok.Data;

@Data
public class MethodNorm {
    private String m;
    private long min=0;
    private long max=0;
    private long sum=0;
    private long total=0;
    private double avg=0D;

    public void setAvg() {
        this.avg = DoubleUtil.avg(sum, total);
    }
    public void add(MethodNorm norm) {
        if (m.equals(norm.m)) {
            if (norm.getMin() < min) {
                min = norm.getMin();
            }
            if (norm.getMax() > max) {
                max = norm.getMax();
            }
            this.sum+=norm.getSum();
            this.total+=norm.total;
            this.setAvg();
        }
    }
}