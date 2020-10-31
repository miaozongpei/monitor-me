package com.m.monitor.me.client.point.collector;

import lombok.Getter;
/**
 * 方法调用节点
 * @Author: miaozp
 * @Date: 2020/10/31 5:36 下午
 **/
@Getter
public class MethodChain {
    private int invokeSeq;
    private String methodName;
    private Integer parentIndex=-1;

    public MethodChain(int invokeSeq, String methodName) {
        this.invokeSeq = invokeSeq;
        this.methodName = methodName;
    }
    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }
    @Override
    public String toString() {
        return invokeSeq+":"+methodName;
    }


}
