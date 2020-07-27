package com.m.monitro.me.common.transfer;

import java.util.LinkedHashMap;

public class IntegratorContext {
    private String monitorName;
    private LinkedHashMap Integrators;

    public IntegratorContext(String monitorName, LinkedHashMap integrators) {
        this.monitorName = monitorName;
        Integrators = integrators;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public LinkedHashMap getIntegrators() {
        return Integrators;
    }

    public void setIntegrators(LinkedHashMap integrators) {
        Integrators = integrators;
    }
}
