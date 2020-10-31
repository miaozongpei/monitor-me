package com.m.monitor.me.service.mogodb.builder;


import com.m.monitor.me.service.transfer.norm.MethodNorm;
import com.m.monitor.me.service.transfer.norm.TimeNorm;
import com.m.monitor.me.service.mogodb.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.enums.QueryNormTypeEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.DoubleUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 监控指标构建器（用于根据时间单位构建指标聚合器）
 *
 * @Author: miaozp
 * @Date: 2020/10/31 2:08 下午
 **/
@Data
public class MonitorNormBuilder {
    /**
     * 当前时间戳
     */
    private long currentTime;
    /**
     * 时间单位
     */
    private MonitorTimeUnitEnum timeUnit;
    /**
     * 时间跨度大小
     */
    private int size;

    /**
     * 前推时间点
     **/
    private long beforeTime;

    public MonitorNormBuilder(long currentTime, MonitorTimeUnitEnum timeUnit, int size) {
        this.currentTime = MonitorTimeUtil.toTime(currentTime, timeUnit);
        this.timeUnit = timeUnit;
        this.size = size;
        this.beforeTime = MonitorTimeUtil.subTime(currentTime, size, timeUnit);
    }
    /**
     * 构建指标数据
     * 根据指标类型（RT、TP）和目标方法（point）对集合指标进行构建，最终获取平均指标值
     * @Author: miaozp
     * @Date: 2020/10/31 2:13 下午
     * @Param: [queryNormType, records, targetMethod]
     * @Return: java.util.List<double[]>
     **/
    public List<double[]> build(String queryNormType, List<IntegratorNormRecord> records, String targetMethod) {
        Map<Long, Double> normMap = initNormMap();
        Set<String> hostSet = new HashSet<>();
        for (IntegratorNormRecord record : records) {
            for (TimeNorm timeNorm : record.getTs()) {
                long targetTime = MonitorTimeUtil.toTime(timeNorm.getT(), timeUnit);
                Double targetNorm = normMap.get(targetTime);
                if (targetNorm != null) {
                    if (StringUtils.isEmpty(targetMethod)) {
                        targetNorm += QueryNormTypeEnum.TP.name().equals(queryNormType) ? timeNorm.getTotal() : timeNorm.getAvg();
                    } else {
                        MethodNorm methodNorm = timeNorm.getMethodNorm(targetMethod);
                        if (methodNorm != null) {
                            targetNorm += QueryNormTypeEnum.TP.name().equals(queryNormType) ? methodNorm.getTotal() : methodNorm.getAvg();
                        }
                    }
                    hostSet.add(record.getHost());
                    normMap.put(targetTime, targetNorm);
                }
            }
        }

        List<double[]> targetList = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : normMap.entrySet()) {
            long time = DateUtil.parse(entry.getKey() + "", DateUtil.FORMAT_YYYYMMDDHHMISS).getTime();
            long avgCount = 1;
            if (hostSet.size() != 0) {
                avgCount = avgCount * hostSet.size();
            }
            targetList.add(new double[]{Double.parseDouble(time + ""), DoubleUtil.avg(entry.getValue(), avgCount)});
        }
        return targetList;
    }
    /**
     * 初始化指标集合（默认0）
     * @Author: miaozp
     * @Date: 2020/10/31 2:17 下午
     * @Param: []
     * @Return: java.util.Map<java.lang.Long,java.lang.Double>
     **/
    private Map<Long, Double> initNormMap() {
        Map<Long, Double> normMap = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            long targetTime = MonitorTimeUtil.subTime(currentTime, i, timeUnit);
            if (normMap.get(targetTime) == null) {
                normMap.put(targetTime, 0D);
            }
        }
        return normMap;
    }

}
