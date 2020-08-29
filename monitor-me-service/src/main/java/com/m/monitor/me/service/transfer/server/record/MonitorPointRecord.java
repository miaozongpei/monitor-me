package com.m.monitor.me.service.transfer.server.record;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MonitorPointRecord {
    private String name;
    private String host;
    private List<MonitorMethod> ms=new ArrayList<>();
}
