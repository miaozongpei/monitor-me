package com.m.monitro.me.common.transfer;

import java.util.LinkedHashMap;

public class IntegratorContext {
    private String name;
    private LinkedHashMap its;

    private String host;

    public IntegratorContext() {
    }

    public IntegratorContext(String name, LinkedHashMap its) {
        this.name = name;
        this.its = its;
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

    public LinkedHashMap getIts() {
        return its;
    }

    public void setIts(LinkedHashMap its) {
        this.its = its;
    }
}
