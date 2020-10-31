package com.m.monitor.me.service.transfer.norm;

import lombok.Data;

/**
 * 慢监控点
 *
 * @Author: miaozp
 * @Date: 2020/10/31 4:01 下午
 **/
@Data
public class SlowMonitorPoint {
    private String name;
    private String m;
    private Double avgNorm;
}
