package com.m.monitor.me.service.transfer.record;

import com.m.monitro.me.common.limit.PointLimit;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MonitorPointRecord {
    private String id;
    private String name;
    private String host;
    private String m;
    private PointLimit ml=new PointLimit();

    private String mc;

    public MonitorPointRecord(String name, String host, String m, String mc) {
        this.name = name;
        this.host = host;
        this.m = m;
        this.mc = mc;
    }
}