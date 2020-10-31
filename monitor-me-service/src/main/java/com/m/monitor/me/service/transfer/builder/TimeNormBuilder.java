package com.m.monitor.me.service.transfer.builder;

import com.m.monitor.me.service.transfer.norm.MethodNorm;
import com.m.monitor.me.service.transfer.norm.TimeNorm;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间指标构建器
 *
 * @Author: miaozp
 * @Date: 2020/10/31 3:08 下午
 **/
@Getter
public class TimeNormBuilder {
    private Map<Long, MethodNormBuilder> secondMap = new HashMap<>();
    private Map<Long, MethodNormBuilder> minuteMap = new HashMap<>();
    private Map<Long, MethodNormBuilder> hourMap = new HashMap<>();
    private Map<Long, MethodNormBuilder> dayMap = new HashMap<>();

    /**
     * 根据时间戳将监控指标分类放入
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:53 下午
     * @Param: [time, normMap]
     * @Return: void
     **/
    public void putTimeNorm(long time, Map normMap) {
        MethodNormBuilder secondMethodNormBuilder = getOrCreate(secondMap, time);
        MethodNormBuilder minuteMethodNormBuilder = getOrCreate(minuteMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.MINUTE));
        MethodNormBuilder hourMethodNormBuilder = getOrCreate(hourMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.HOUR));
        MethodNormBuilder dayMethodNormBuilder = getOrCreate(dayMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.DAY));
        for (Object key : normMap.keySet()) {
            MethodNorm methodNorm = new MethodNorm();
            methodNorm.setM((String) key);
            Map methodValue = (Map) normMap.get(key);
            methodNorm.setMax((Integer) methodValue.get("maxNorm"));
            methodNorm.setMin((Integer) methodValue.get("minNorm"));
            methodNorm.setSum((Integer) methodValue.get("sumNorm"));
            methodNorm.setTotal((Integer) methodValue.get("total"));

            secondMethodNormBuilder.put(methodNorm);
            minuteMethodNormBuilder.put(methodNorm);
            hourMethodNormBuilder.put(methodNorm);
            dayMethodNormBuilder.put(methodNorm);
        }
    }

    /**
     * 根据时间戳和分类枚举放入指标集合
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:57 下午
     * @Param: [time, methodNorm, unitEnum]
     * @Return: void
     **/
    public void putTimeNorm(long time, MethodNorm methodNorm, MonitorTimeUnitEnum unitEnum) {
        if (unitEnum == MonitorTimeUnitEnum.SECOND) {
            MethodNormBuilder secondMethodNormBuilder = getOrCreate(secondMap, time);
            secondMethodNormBuilder.put(methodNorm);
        } else if (unitEnum == MonitorTimeUnitEnum.MINUTE) {
            MethodNormBuilder minuteMethodNormBuilder = getOrCreate(minuteMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.MINUTE));
            minuteMethodNormBuilder.put(methodNorm);
        } else if (unitEnum == MonitorTimeUnitEnum.HOUR) {
            MethodNormBuilder hourMethodNormBuilder = getOrCreate(hourMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.HOUR));
            hourMethodNormBuilder.put(methodNorm);
        } else if (unitEnum == MonitorTimeUnitEnum.DAY) {
            MethodNormBuilder dayMethodNormBuilder = getOrCreate(dayMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.DAY));
            dayMethodNormBuilder.put(methodNorm);
        }
    }

    /**
     * 获取或者创建监控点构造器
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:59 下午
     * @Param: [map, t]
     * @Return: com.m.monitor.me.service.transfer.builder.MethodNormBuilder
     **/
    private MethodNormBuilder getOrCreate(Map<Long, MethodNormBuilder> map, Long t) {
        MethodNormBuilder methodNormBuilder = map.get(t);
        if (methodNormBuilder == null) {
            methodNormBuilder = new MethodNormBuilder();
            map.put(t, methodNormBuilder);
        }
        return methodNormBuilder;
    }

    /**
     * 构建时间指标
     *
     * @Author: miaozp
     * @Date: 2020/10/31 3:59 下午
     * @Param: [map]
     * @Return: java.util.List<com.m.monitor.me.service.transfer.norm.TimeNorm>
     **/
    public List<TimeNorm> buildTimeNorms(Map<Long, MethodNormBuilder> map) {
        List<TimeNorm> timeNorms = new ArrayList<>();
        for (Map.Entry<Long, MethodNormBuilder> entry : map.entrySet()) {
            Long time = entry.getKey();
            MethodNormBuilder methodNormBuilder = entry.getValue();
            timeNorms.add(buildTimeNorm(time, methodNormBuilder));
        }
        return timeNorms;
    }

    /**
     * 单个构建时间指标
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:00 下午
     * @Param: [time, methodNormBuilder]
     * @Return: com.m.monitor.me.service.transfer.norm.TimeNorm
     **/
    public TimeNorm buildTimeNorm(Long time, MethodNormBuilder methodNormBuilder) {
        TimeNorm timeNorm = new TimeNorm(time);
        timeNorm.getMs().addAll(methodNormBuilder.getMethodNormMap().values());
        timeNorm.cal();//计算平均
        return timeNorm;
    }
}
