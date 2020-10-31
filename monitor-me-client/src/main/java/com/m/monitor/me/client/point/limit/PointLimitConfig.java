package com.m.monitor.me.client.point.limit;

import com.m.monitro.me.common.limit.PointLimit;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
/**
 * 限流规则集合
 * @Author: miaozp
 * @Date: 2020/10/31 5:57 下午
 **/
public class PointLimitConfig {
    public static Map<String, PointLimit> limitConfig=new HashMap<>();

    public static Map<String, PointLimit> emptyConfig=new HashMap<>();

    public static PointLimit get(String method){
        PointLimit pointLimit=limitConfig.get(method);
        return pointLimit;
    }
}
