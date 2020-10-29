package com.m.monitro.me.common.transfer;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegratorContext {
    private String name;
    private Map its;
    private Map<String,String> mcs;
    private String host;

    public IntegratorContext() {
    }

    public IntegratorContext(String name, Map its,Map<String,String> mcs) {
        this.name = name;
        this.its = its;
        this.mcs=mcs;
    }

    public String getName() {
        return name;
    }


    public String getHost() {
        return host;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public Map getIts() {
        return its;
    }

    public void setIts(LinkedHashMap its) {
        this.its = its;
    }

    public Map<String, String> getMcs() {
        return mcs;
    }

    public void setMcs(Map<String, String> mcs) {
        this.mcs = mcs;
    }
}
