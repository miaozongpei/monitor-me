package com.m.monitor.me.service.mogodb.norm;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.norm.SlowMonitorPoint;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import com.m.monitor.me.service.transfer.record.MonitorHostRecord;
import com.m.monitro.me.common.enums.PointLimitSynStatusEnum;
import com.m.monitro.me.common.limit.PointLimit;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class MonitorPointService extends BaseMongoService<MonitorPointRecord> {
    private String collectionName="monitor_point";
    public MonitorPointRecord queryOne(String name, String host, String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("m").is(method));
        return this.mongoTemplate.findOne(query, MonitorPointRecord.class,collectionName);
    }
    public List<String> queryMethods(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"m",collectionName, MonitorPointRecord.class,String.class);
    }
    public List<String> queryMethodsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"m",collectionName, MonitorPointRecord.class,String.class);
    }
    public List<String> queryHosts(String name,String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorPointRecord.class,String.class);
    }
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
    public void modifyMl(String name, String method,String[] hosts,PointLimit pointLimit){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        query.addCriteria(Criteria.where("host").in(hosts));
        Update update=new Update();
        update.set("ml",pointLimit);
        UpdateResult updateResult= mongoTemplate.updateMulti(query,update, MonitorPointRecord.class,collectionName);
    }
    public List<MonitorPointRecord> queryPointLimit(String name, String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("ml").exists(true));
        return find(collectionName,query,MonitorPointRecord.class);
    }
    public List<MonitorPointRecord> queryList(String name,String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("m").is(method));
        return find(collectionName,query,MonitorPointRecord.class);
    }
    public List<SlowMonitorPoint> querySlow(int limit){
        Aggregation aggregation= Aggregation.newAggregation(
                Aggregation.group("name","m").avg("mc.norm").as("avgNorm"),
                Aggregation.limit(limit),
                Aggregation.sort(new Sort(Sort.Direction.DESC, "avgNorm"))
        );
        AggregationResults<SlowMonitorPoint> results =
                mongoTemplate.aggregate(aggregation, collectionName, SlowMonitorPoint.class);
        return results.getMappedResults();
    }
}
