package com.m.monitor.me.client.point.collector;

import lombok.Getter;

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
