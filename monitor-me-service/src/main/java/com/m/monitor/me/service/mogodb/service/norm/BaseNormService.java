package com.m.monitor.me.service.mogodb.service.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.mogodb.builder.MonitorNormBuilder;
import com.m.monitor.me.service.transfer.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.transfer.norm.TimeNorm;
import com.m.monitor.me.service.mogodb.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * 监控指标基础Service类
 * 通过定义抽象方法getCollectionName和MonitorTimeUnitEnum来确定mongoDB的collection
 * 监控指标按照时间单位分成：按秒、按分钟、按小时、按天的汇总指标
 * 本基础类提供的能力有：1、根据指标聚合器保存或者更新指标；2、独立CRU能力；3、根据不通条件查询某一时间跨度的指标集合
 * @Author: miaozp
 * @Date: 2020/10/31 11:50 上午
 **/
public abstract class BaseNormService extends BaseMongoService<IntegratorNormRecord> {
    /**
     * 获取collection名称
     * @Author: miaozp
     * @Date: 2020/10/31 4:46 下午
     * @return: java.lang.String
     **/
    public abstract String getCollectionName();
    /**
     * 获取监控时间单位枚举
     * @Author: miaozp
     * @Date: 2020/10/31 4:48 下午
     * @return: com.m.monitro.me.common.enums.MonitorTimeUnitEnum
     **/
    public abstract MonitorTimeUnitEnum getMonitorTimeUnitEnum();
    /**
     * 通过指标聚合器保存或者更新时间指标
     * @Author: miaozp
     * @Date: 2020/10/31 12:05 下午
     * @Param: [integratorNormBuilder]
     * @Return: void
     **/
    public void saveOrModify(IntegratorNormBuilder integratorNormBuilder) {
        String name = integratorNormBuilder.getName();
        String host = integratorNormBuilder.getHost();
        //根据时间单位枚举获取目标集合器持久化对象
        IntegratorNormRecord record = integratorNormBuilder.getIntegratorNormRecord(getMonitorTimeUnitEnum());
        if (record != null) {
            //因为在时间单位临界点会出现跨度问题，所以可能会产生最多2条记录，toList是将IntegratorNormBuilder过后的集合器进行重新组装
            List<IntegratorNormRecord> records = record.toList();
            for (IntegratorNormRecord integratorNormRecord : record.toList()) {
                saveOrModify(integratorNormBuilder, integratorNormRecord);
            }
        }
    }
    /**
     * 保存或者更新时间指标
     * 聚合指标不存在则保存；反之，则更新
     * @Author: miaozp
     * @Date: 2020/10/31 12:17 下午
     * @Param: [integratorNormBuilder, integratorNormRecord]
     * @Return: void
     **/
    private void saveOrModify(IntegratorNormBuilder integratorNormBuilder, IntegratorNormRecord integratorNormRecord) {
        String name = integratorNormRecord.getName();
        String host = integratorNormRecord.getHost();
        Long time = integratorNormRecord.getTs().get(0).getT();
        IntegratorNormRecord current = queryOne(name, host, time);
        if (current == null) {
            save(integratorNormRecord);
        } else {
            //更新指标过程中需要累计之前数据
            integratorNormBuilder.putBeforeIntegratorNormRecord(getMonitorTimeUnitEnum(), current);
            TimeNorm targetTimeNorm = integratorNormBuilder.getTimeNormBuilder(time, getMonitorTimeUnitEnum());
            List<TimeNorm> ts = new ArrayList<>();
            ts.add(targetTimeNorm);
            integratorNormRecord.setTs(ts);
            modify(current.getId(), integratorNormRecord);
        }
    }

    /**
     * 保存指标
     * @Author: miaozp
     * @Date: 2020/10/31 12:21 下午
     * @Param: [integratorNormRecord]
     * @Return: void
     **/
    public void save(IntegratorNormRecord integratorNormRecord) {
        mongoTemplate.insert(integratorNormRecord, getCollectionName());
    }
    /**
     * 更新指标
     * @Author: miaozp
     * @Date: 2020/10/31 12:21 下午
     * @Param: [id, update]
     * @Return: void
     **/
    public void modify(String id, IntegratorNormRecord update) {
        this.update(getCollectionName(), id, update);
    }
    /**
     * 查询唯一指标
     * @Author: miaozp
     * @Date: 2020/10/31 12:22 下午
     * @Param: [name, host, time]
     * @Return: com.m.monitor.me.service.transfer.record.IntegratorNormRecord
     **/
    public IntegratorNormRecord queryOne(String name, String host, Long time) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("ts.t").is(time));
        return this.mongoTemplate.findOne(query, IntegratorNormRecord.class, getCollectionName());
    }
    /**
     * 根据不通条件查询某一时间跨度下的指标集合
     * @Author: miaozp
     * @Date: 2020/10/31 12:22 下午
     * @Param: [normType, name, host, methodName, currentTime, size]
     * @Return: java.util.List<double[]>
     **/
    public List<double[]> queryRealTimeNorm(String normType, String name, String host,
                                            String methodName,
                                            long currentTime,
                                            int size) {
        MonitorNormBuilder builder = new MonitorNormBuilder(currentTime, getMonitorTimeUnitEnum(), size);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        if (!StringUtils.isEmpty(host)) {
            query.addCriteria(Criteria.where("host").is(host));
        }
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").gte(builder.getBeforeTime()).lt(currentTime));
        List<IntegratorNormRecord> list = this.find(getCollectionName(), query, IntegratorNormRecord.class);
        return builder.build(normType, list, methodName);
    }
    /**
     *
     * @Author: miaozp
     * @Date: 2020/10/31 12:26 下午
     * @Param: [normType, name, host, methodName, currentTime]
     * @Return: double[]
     **/
    public double[] queryRealTimeNorm(String normType, String name, String host,
                                      String methodName,
                                      long currentTime) {
        MonitorNormBuilder builder = new MonitorNormBuilder(currentTime, getMonitorTimeUnitEnum(), 1);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").is(currentTime));
        List<IntegratorNormRecord> list = this.find(getCollectionName(), query, IntegratorNormRecord.class);
        List<double[]> result = builder.build(normType, list, methodName);
        return CollectionUtils.isEmpty(result) ? new double[]{Double.parseDouble(currentTime + ""), 0D} : result.get(0);
    }

}
