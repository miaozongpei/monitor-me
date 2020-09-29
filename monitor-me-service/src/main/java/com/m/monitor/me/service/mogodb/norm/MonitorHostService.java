package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.record.MonitorHostRecord;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitorHostService extends BaseMongoService<MonitorHostRecord> {
    private String collectionName="monitor_host";
    public MonitorHostRecord queryOne(String name, String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        return this.mongoTemplate.findOne(query, MonitorHostRecord.class,collectionName);
    }
    public void saveOrModify(MonitorHostRecord monitorHostRecord){
        String name= monitorHostRecord.getName();
        String host= monitorHostRecord.getHost();
        MonitorHostRecord current=queryOne(name,host);
        if (current==null){
            insert(collectionName, monitorHostRecord);
        }else {
            current.setStatus(monitorHostRecord.getStatus());
            update(collectionName,current.getId(),current);
        }
    }
    public List<String> queryNames(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"name",collectionName, MonitorHostRecord.class,String.class);
    }
    public List<String> queryHosts(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorHostRecord.class,String.class);
    }

    public List<String> queryHostsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorHostRecord.class,String.class);
    }
}
