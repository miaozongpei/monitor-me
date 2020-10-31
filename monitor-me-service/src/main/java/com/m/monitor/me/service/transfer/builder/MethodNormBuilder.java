package com.m.monitor.me.service.transfer.builder;

import com.m.monitor.me.service.transfer.norm.MethodNorm;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 监控方法（监控点）构建器
 *
 * @Author: miaozp
 * @Date: 2020/10/31 3:01 下午
 **/
@Getter
public class MethodNormBuilder {
    private Map<String, MethodNorm> methodNormMap = new HashMap<>();

    /**
     * 放入监控点集合
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:01 下午
     **/
    public void put(MethodNorm methodNorm) {
        getOrCreate(methodNorm.getM()).add(methodNorm);
    }

    /**
     * 获取或者创建监控点
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:02 下午
     * @Param: [method]
     * @Return: com.m.monitor.me.service.transfer.norm.MethodNorm
     **/
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
