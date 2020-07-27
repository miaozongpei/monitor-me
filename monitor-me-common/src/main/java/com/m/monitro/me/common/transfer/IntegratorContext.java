package com.m.monitro.me.common.transfer;

import java.util.LinkedHashMap;

public class IntegratorContext {
    private String name;
    private LinkedHashMap Integrators;

    private String host;

    public IntegratorContext() {
    }

    public IntegratorContext(String name, LinkedHashMap integrators) {
        this.name = name;
        Integrators = integrators;
    }

    public String getName() {
        return name;
    }

    public LinkedHashMap getIntegrators() {
        return Integrators;
    }

    public String getHost() {
        return host;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntegrators(LinkedHashMap integrators) {
        Integrators = integrators;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
