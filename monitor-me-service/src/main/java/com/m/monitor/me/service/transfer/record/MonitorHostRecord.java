package com.m.monitor.me.service.transfer.record;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class MonitorHostRecord {
    private String id;
    private String name;
    private String host;
    private String status="0";//正常

    public MonitorHostRecord(String name, String host) {
        this.name = name;
        this.host = host;
    }
}
