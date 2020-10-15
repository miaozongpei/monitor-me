package com.m.monitor.me.service.transfer.record;

import com.m.monitor.me.service.transfer.norm.MethodChan;
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
    private PointLimit ml;

    private MethodChan mc;

    public MonitorPointRecord() {
    }

    public MonitorPointRecord(String name, String host, String m, String mc) {
        this.name = name;
        this.host = host;
        this.m = m;
        this.mc = new MethodChan(mc);
    }

    public MonitorPointRecord(String id, String name, String host, String m, PointLimit ml, MethodChan mc) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.m = m;
        this.ml = ml;
        this.mc = mc;
    }
}
