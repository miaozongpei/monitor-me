package com.m.monitor.me.service.transfer.server.builder;

import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class MethodNormBuilder {
    private Map<String, MethodNorm> secondMap = new HashMap<>();
    private Map<String, MethodNorm> minuteMap = new HashMap<>();

    public void put(MethodNorm methodNorm) {
        getOrCreate(secondMap, methodNorm.getM()).add(methodNorm);
        getOrCreate(minuteMap, methodNorm.getM()).add(methodNorm);
    }

    private MethodNorm getOrCreate(Map<String, MethodNorm> map, String method) {
        MethodNorm methodNorm = map.get(method);
        if (methodNorm == null) {
            methodNorm = new MethodNorm();
            methodNorm.setM(method);
            map.put(method, methodNorm);
        }
        return methodNorm;
    }
}
