package com.m.monitro.me.common.enums;

public enum MonitorTimeUnitEnum {
    SECOND(1),
    MINUTE(60),
    HOUR(60*60),
    HOUR_6(6*60*60),
    HOUR_12(6*60*60),
    HOUR_24(6*60*60),

    DAY(24*60*60),
    DAY_7(7*24*60*60),
    DAY_30(30*24*60*60),
    MONTH(30*24*60*60),
    ;
    public long unit;
    MonitorTimeUnitEnum(long unit){
        this.unit=unit;
    }
}
