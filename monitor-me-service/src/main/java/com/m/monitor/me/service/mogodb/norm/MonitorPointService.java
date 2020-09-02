package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.record.MonitorPointRecord;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitorPointService extends BaseMongoService<MonitorPointRecord> {
    private String collectionName="monitor_point";
    public MonitorPointRecord queryOne(String name,String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        return this.mongoTemplate.findOne(query,MonitorPointRecord.class,collectionName);
    }
    public void saveOrModify(MonitorPointRecord monitorPointRecord){
        String name=monitorPointRecord.getName();
        String host=monitorPointRecord.getHost();
        MonitorPointRecord current=queryOne(name,host);
        if (current==null){
            insert(collectionName,monitorPointRecord);
        }else {
            current.setStatus(monitorPointRecord.getStatus());
            current.getMs().addAll(monitorPointRecord.getMs());
            update(collectionName,current.getId(),current);
        }
    }
    public List<String> queryNames(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"name",collectionName,MonitorPointRecord.class,String.class);
    }
    public List<String> queryHosts(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"host",collectionName,MonitorPointRecord.class,String.class);
    }
    public List<String> queryMethods(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"ms.m",collectionName,MonitorPointRecord.class,String.class);
    }
    public List<String> queryHostsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"host",collectionName,MonitorPointRecord.class,String.class);
    }
    public List<String> queryMethodsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"ms.m",collectionName,MonitorPointRecord.class,String.class);
    }
    public List<String> queryMethodsByHost(String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("host").is(host));
        return mongoTemplate.findDistinct(query,"ms.m",collectionName,MonitorPointRecord.class,String.class);
    }


}
