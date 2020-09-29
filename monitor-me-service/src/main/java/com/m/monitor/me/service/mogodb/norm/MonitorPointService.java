package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import com.m.monitor.me.service.transfer.record.MonitorHostRecord;
import com.m.monitro.me.common.enums.PointLimitSynStatusEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public void saveOrModifyMl(MonitorPointRecord monitorPointRecord){
        String name=monitorPointRecord.getName();
        String host=monitorPointRecord.getHost();
        String method=monitorPointRecord.getM();
        MonitorPointRecord current=queryOne(name,host,method);
        if (current==null){
            insert(collectionName,monitorPointRecord);
        }else {
            current.setMl(monitorPointRecord.getMl());
            update(collectionName,current.getId(),current);
        }
    }
    public List<MonitorPointRecord> queryPointLimit(String name, String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("ml").exists(true));
        return find(collectionName,query,MonitorPointRecord.class);
    }
}
