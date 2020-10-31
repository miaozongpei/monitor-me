package com.m.monitor.me.service.transfer.builder;

import com.m.monitor.me.service.transfer.norm.MethodNorm;
import com.m.monitor.me.service.transfer.norm.TimeNorm;
import com.m.monitor.me.service.mogodb.record.MonitorHostRecord;
import com.m.monitor.me.service.mogodb.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.transfer.IntegratorContext;
import lombok.Getter;

import java.util.Map;

/**
 * 聚合器指标构建器
 *
 * @Author: miaozp
 * @Date: 2020/10/31 2:47 下午
 **/
@Getter
public class IntegratorNormBuilder {
    /**
     * 秒数聚合器指标记录
     **/
    private IntegratorNormRecord secondRecord;
    /**
     * 分钟聚合器指标记录
     **/
    private IntegratorNormRecord minuteRecord;
    /**
     * 小时聚合器指标记录
     **/
    private IntegratorNormRecord hourRecord;
    /**
     * 天数聚合器指标记录
     **/
    private IntegratorNormRecord dayRecord;
    /**
     * 监控系统记录
     **/
    private MonitorHostRecord monitorHostRecord;


    /**
     * 指标构建器（格式化时间）
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:51 下午
     **/
    private TimeNormBuilder timeNormBuilder = new TimeNormBuilder();
    /**
     * 聚合器传输上下文
     **/
    private IntegratorContext context;
    /**
     * 服务器IP
     **/
    private String host;
    /**
     * 系统名称
     **/
    private String name;

    /**
     * 构造方法
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:54 下午
     * @Param: [context]
     **/
    public IntegratorNormBuilder(IntegratorContext context) {
        this.context = context;
        this.name = context.getName();
        this.host = context.getHost();
        putIntegratorContext();
    }

    /**
     * 组装构建
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:54 下午
     * @Param: []
     * @Return: com.m.monitor.me.service.transfer.builder.IntegratorNormBuilder
     **/
    public IntegratorNormBuilder build() {
        secondRecord = new IntegratorNormRecord(name, host);
        minuteRecord = new IntegratorNormRecord(name, host);
        hourRecord = new IntegratorNormRecord(name, host);
        dayRecord = new IntegratorNormRecord(name, host);

        monitorHostRecord = new MonitorHostRecord(name, host);

        secondRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getSecondMap()));
        minuteRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getMinuteMap()));
        hourRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getHourMap()));
        dayRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getDayMap()));
        return this;
    }

    /**
     * 拆分传输上下文中的指标
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:56 下午
     * @Param: []
     * @Return: void
     **/
    private void putIntegratorContext() {
        for (Object key : context.getIts().keySet()) {
            Long time = (Long) key;
            timeNormBuilder.putTimeNorm(time, (Map) context.getIts().get(key));
        }
    }

    /**
     * 累计之前数据
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:58 下午
     * @Param: [unitEnum, integratorNormRecord]
     * @Return: void
     **/
    public void putBeforeIntegratorNormRecord(MonitorTimeUnitEnum unitEnum, IntegratorNormRecord integratorNormRecord) {
        for (TimeNorm timeNorm : integratorNormRecord.getTs()) {
            long time = timeNorm.getT();
            for (MethodNorm methodNorm : timeNorm.getMs()) {
                timeNormBuilder.putTimeNorm(time, methodNorm, unitEnum);
            }
        }
    }

    /**
     * 根据时间单位获取对于的指标记录
     *
     * @Author: miaozp
     * @Date: 2020/10/31 2:59 下午
     * @Param: [unitEnum]
     * @Return: com.m.monitor.me.service.transfer.record.IntegratorNormRecord
     **/
    public IntegratorNormRecord getIntegratorNormRecord(MonitorTimeUnitEnum unitEnum) {
        if (unitEnum == MonitorTimeUnitEnum.SECOND) {
            return secondRecord;
        } else if (unitEnum == MonitorTimeUnitEnum.MINUTE) {
            return minuteRecord;
        } else if (unitEnum == MonitorTimeUnitEnum.HOUR) {
            return hourRecord;
        } else if (unitEnum == MonitorTimeUnitEnum.DAY) {
            return dayRecord;
        }
        return null;
    }

    /**
     * 根据时间单位获取对于的指标时间构建器
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:00 下午
     * @Param: [time, unitEnum]
     * @Return: com.m.monitor.me.service.transfer.norm.TimeNorm
     **/
    public TimeNorm getTimeNormBuilder(Long time, MonitorTimeUnitEnum unitEnum) {
        if (unitEnum == MonitorTimeUnitEnum.SECOND) {
            return timeNormBuilder.buildTimeNorm(time, timeNormBuilder.getSecondMap().get(time));
        } else if (unitEnum == MonitorTimeUnitEnum.MINUTE) {
            return timeNormBuilder.buildTimeNorm(time, timeNormBuilder.getMinuteMap().get(time));
        } else if (unitEnum == MonitorTimeUnitEnum.HOUR) {
            return timeNormBuilder.buildTimeNorm(time, timeNormBuilder.getHourMap().get(time));
        } else if (unitEnum == MonitorTimeUnitEnum.DAY) {
            return timeNormBuilder.buildTimeNorm(time, timeNormBuilder.getDayMap().get(time));
        }
        return null;
    }

}
