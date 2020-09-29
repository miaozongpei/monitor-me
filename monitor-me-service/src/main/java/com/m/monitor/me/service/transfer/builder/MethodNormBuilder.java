package com.m.monitor.me.service.transfer.builder;

import com.m.monitor.me.service.transfer.norm.MethodNorm;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class MethodNormBuilder {
    private Map<String, MethodNorm> methodNormMap = new HashMap<>();
    public void put(MethodNorm methodNorm) {
        getOrCreate(methodNorm.getM()).add(methodNorm);
    }

    private MethodNorm getOrCreate(String method) {
        MethodNorm methodNorm = methodNormMap.get(method);
        if (methodNorm == null) {
            methodNorm = new MethodNorm();
            methodNorm.setM(method);
            methodNormMap.put(method, methodNorm);
        }
        return methodNorm;
    }

}
