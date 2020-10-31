package com.m.monitor.me.service.mogodb.record;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 系统监控记录
 * @Author: miaozp
 * @Date: 2020/10/31 3:04 下午
 **/
@Data
public class MonitorHostRecord {
    private String id;
    private String name;
    private String host;
    private String status="0";

    public MonitorHostRecord(String name, String host) {
        this.name = name;
        this.host = host;
    }
}
