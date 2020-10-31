package com.m.monitor.me.service.mogodb.service;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.mogodb.record.MonitorHostRecord;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * 系统监控服务Service
 * @Author: miaozp
 * @Date: 2020/10/31 2:18 下午
 **/
@Component
public class MonitorHostService extends BaseMongoService<MonitorHostRecord> {
    private String collectionName="monitor_host";
    /**
     * 获取唯一系统服务记录
     * @Author: miaozp
     * @Date: 2020/10/31 2:24 下午
     * @Param: [name, host]
     * @Return: com.m.monitor.me.service.transfer.record.MonitorHostRecord
     **/
    public MonitorHostRecord queryOne(String name, String host){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        return this.mongoTemplate.findOne(query, MonitorHostRecord.class,collectionName);
    }
    /**
     * 保存或者更新系统服务记录
     * @Author: miaozp
     * @Date: 2020/10/31 2:25 下午
     * @Param: [monitorHostRecord]
     * @Return: void
     **/
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
    /**
     * 获取所监控系统名称
     * @Author: miaozp
     * @Date: 2020/10/31 2:25 下午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryNames(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"name",collectionName, MonitorHostRecord.class,String.class);
    }
    /**
     * 获取所监控服务器IP
     * @Author: miaozp
     * @Date: 2020/10/31 2:26 下午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryHosts(){
        Query query = new Query();
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorHostRecord.class,String.class);
    }
    /**
     * 根据系统名称获取所监控服务器IP
     * @Author: miaozp
     * @Date: 2020/10/31 2:27 下午
     * @Param: [name]
     * @Return: java.util.List<java.lang.String>
     **/
    public List<String> queryHostsByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findDistinct(query,"host",collectionName, MonitorHostRecord.class,String.class);
    }
}
