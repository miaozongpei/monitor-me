package com.m.monitor.me.service.transfer.server.record;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class MonitorPointRecord {
    private String id;
    private String name;
    private String host;
    private String status="0";//正常

    public MonitorPointRecord(String name, String host) {
        this.name = name;
        this.host = host;
    }
}
