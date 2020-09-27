package com.m.monitor.me.service.transfer.server.record;

import lombok.Data;

import java.util.Objects;

@Data
public class MonitorMethod {
    private String m;//方法名称
    private String mc;//方法调用链

    public MonitorMethod(String m) {
        this.m = m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        MonitorMethod method = (MonitorMethod) o;
        return m.equals(method.m);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m);
    }
}
