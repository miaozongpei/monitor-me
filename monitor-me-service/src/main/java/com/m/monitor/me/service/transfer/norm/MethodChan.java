package com.m.monitor.me.service.transfer.norm;

import lombok.Data;
public class MethodChan {
    private long norm;
    private String chain;

    public MethodChan() {
    }

    public MethodChan(String chain) {
        this.chain = chain;
        splitBuildNorm(chain);
    }

    private void splitBuildNorm(String chain){
        String[] s=chain.split("\\|");
        String normStr=s[1].replaceFirst("ms","");
        this.norm=Long.parseLong(normStr);
    }

    public long getNorm() {
        return norm;
    }

    public void setNorm(long norm) {
        this.norm = norm;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }
}
