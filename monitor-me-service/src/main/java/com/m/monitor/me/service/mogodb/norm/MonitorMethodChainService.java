package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.record.MonitorMethodChainRecord;
import com.m.monitor.me.service.transfer.server.record.MonitorPointRecord;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonitorMethodChainService extends BaseMongoService<MonitorMethodChainRecord> {
    private String collectionName="monitor_method_chain";
    public MonitorMethodChainRecord queryOne(String name, String host, String method){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("m").is(method));
        return this.mongoTemplate.findOne(query,MonitorMethodChainRecord.class,collectionName);
    }
    public void saveOrModify(MonitorMethodChainRecord methodChainRecord){
        String name=methodChainRecord.getName();
        String host=methodChainRecord.getHost();
        String method=methodChainRecord.getM();
        MonitorMethodChainRecord current=queryOne(name,host,method);
        if (current==null){
            insert(collectionName,methodChainRecord);
        }else {
            current.setMc(methodChainRecord.getMc());
            update(collectionName,current.getId(),current);
        }
    }

}