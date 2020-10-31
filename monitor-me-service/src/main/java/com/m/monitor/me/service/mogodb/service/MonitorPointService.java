package com.m.monitor.me.service.mogodb.service;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.norm.SlowMonitorPoint;
import com.m.monitor.me.service.mogodb.record.MonitorPointRecord;
import com.m.monitro.me.common.limit.PointLimit;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;
/**
 * 监控点Service类
 * @Author: miaozp
 * @Date: 2020/10/31 2:31 下午
 * @Param: * @param null:
 * @Return: * @return: null
 **/
@Component
public class MonitorPointService extends BaseMongoService<MonitorPointRecord> {
    private String collectionName="monitor_point";
    /**
     * 获取唯一监控点（系统、IP、method）
     * @Author: miaozp
     * @Date: 2020/10/31 2:31 下午
     * @Param: [name, host, method]
     * @Return: com.m.monitor.me.service.transfer.record.MonitorPointRecord
     **/
    public MonitorPointRecord queryOne(String name, String host, String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("m").is(method));
        return this.mongoTemplate.findOne(query, MonitorPointRecord.class,collectionName);
    }
    /**
     * 获取所有监控点名称
     * @Author: miaozp
     * @Date: 2020/10/31 2:32 下午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryMethods(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"m",collectionName, MonitorPointRecord.class,String.class);
    }
    /**
     * 根据系统名称获取所有监控点名称
     * @Author: miaozp
     * @Date: 2020/10/31 2:33 下午
     * @Param: [name]
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryMethodsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"m",collectionName, MonitorPointRecord.class,String.class);
    }
    /**
     * 根据系统名称、监控点名称获取所部署的IP
     * @Author: miaozp
     * @Date: 2020/10/31 2:34 下午
     * @Param: [name, method]
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryHosts(String name,String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorPointRecord.class,String.class);
    }
    /**
     * 保存监控点或者更新监控点调用链
     * @Author: miaozp
     * @Date: 2020/10/31 2:35 下午
     * @Param: [monitorPointRecord]
     * @Return: void
     **/
    public void saveOrModifyMc(MonitorPointRecord monitorPointRecord){
        String name=monitorPointRecord.getName();
        String host=monitorPointRecord.getHost();
        String method=monitorPointRecord.getM();
        MonitorPointRecord current=queryOne(name,host,method);
        if (current==null){
            insert(collectionName,monitorPointRecord);
        }else {
            current.setMc(monitorPointRecord.getMc());
            update(collectionName,current.getId(),current);
        }
    }
    /**
     * 更新监控点上的限流规则（阻断、限流）
     * @Author: miaozp
     * @Date: 2020/10/31 2:36 下午
     * @Param: [name, method, hosts, pointLimit]
     * @Return: void
     **/
    public void modifyMl(String name, String method,String[] hosts,PointLimit pointLimit){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        query.addCriteria(Criteria.where("host").in(hosts));
        Update update=new Update();
        update.set("ml",pointLimit);
        UpdateResult updateResult= mongoTemplate.updateMulti(query,update, MonitorPointRecord.class,collectionName);
    }
    /**
     * 查询某服务器上的某系统上的所有限流规则
     * @Author: miaozp
     * @Date: 2020/10/31 2:37 下午
     * @Param: [name, host] 
     * @Return: java.util.List<com.m.monitor.me.service.transfer.record.MonitorPointRecord>
     **/
    public List<MonitorPointRecord> queryPointLimit(String name, String host){
        Query query = new Query();
        if (!StringUtils.isEmpty(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (!StringUtils.isEmpty(host)) {
            query.addCriteria(Criteria.where("host").is(host));
        }
        query.addCriteria(Criteria.where("ml").ne(null));
        return find(collectionName,query,MonitorPointRecord.class);
    }
    /**
     * 获取监控点上流控规则个数（检查是否存在流控规则）
     * @Author: miaozp
     * @Date: 2020/10/31 2:39 下午
     * @Param: [name, host, method] 
     * @Return: long
     **/
    public long queryPointLimitCount(String name, String host,String method){
        Query query = new Query();
        if (!StringUtils.isEmpty(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (!StringUtils.isEmpty(host)) {
            query.addCriteria(Criteria.where("host").is(host));
        }
        if (!StringUtils.isEmpty(method)) {
            query.addCriteria(Criteria.where("m").is(method));
        }
        query.addCriteria(Criteria.where("ml").ne(null));

        return mongoTemplate.count(query,MonitorPointRecord.class,collectionName);
    }
    /**
     * 获取查询某服务器上的某系统上的所有监控点
     * @Author: miaozp
     * @Date: 2020/10/31 2:41 下午
     * @Param: [name, method] 
     * @Return: java.util.List<com.m.monitor.me.service.transfer.record.MonitorPointRecord>
     **/
    public List<MonitorPointRecord> queryList(String name,String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        return find(collectionName,query,MonitorPointRecord.class);
    }
    /**
     * 根据关键字检索慢指标Top limit（指标降序）
     * @Author: miaozp
     * @Date: 2020/10/31 2:42 下午
     * @Param: [searchKey, isLimit, limit] 
     * @Return: java.util.List<com.m.monitor.me.service.transfer.norm.SlowMonitorPoint>
     **/
    public List<SlowMonitorPoint> querySlow(String searchKey,boolean isLimit,int limit){
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(searchKey)) {
            Pattern pattern = Pattern.compile("^.*" + searchKey + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(
                    Criteria.where("name").regex(pattern),
                    Criteria.where("m").regex(pattern),
                    Criteria.where("host").regex(pattern)
            );
        }
        if(isLimit) {
            criteria.andOperator(Criteria.where("ml").ne(null));
        }

        Aggregation aggregation= Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("name","m").avg("mc.norm").as("avgNorm"),
                Aggregation.limit(limit),
                Aggregation.sort(new Sort(Sort.Direction.DESC, "avgNorm"))
        );
        AggregationResults<SlowMonitorPoint> results =
                mongoTemplate.aggregate(aggregation, collectionName, SlowMonitorPoint.class);
        return results.getMappedResults();
    }
}
