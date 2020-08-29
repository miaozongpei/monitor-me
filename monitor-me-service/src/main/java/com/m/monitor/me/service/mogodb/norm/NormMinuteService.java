package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class NormMinuteService extends BaseMongoService<IntegratorNormRecord>{
    private String collectionName="norm_minute";
    public void saveOrModify(IntegratorNormRecord integratorNormRecord){
        String name=integratorNormRecord.getName();
        String host=integratorNormRecord.getHost();
        Long time=integratorNormRecord.getTs().get(0).getT();
        IntegratorNormRecord current=queryOne(name,host,time);
        if (current==null){
            save(integratorNormRecord);
        }else {
            modify(current.getId(),current.add(integratorNormRecord));
        }
    }
    public void save(IntegratorNormRecord integratorNormRecord){
        mongoTemplate.insert(integratorNormRecord.toList(),collectionName);
    }
    public void modify(String id,IntegratorNormRecord update){
        this.update(collectionName,id,update);
    }
    public IntegratorNormRecord queryOne(String name,String host,Long time){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("ts.t").is(time));
        return this.mongoTemplate.findOne(query,IntegratorNormRecord.class,collectionName);
    }
    public List<double[]> queryRealTimeNorm(String host,
                                            String methodName,
                                            long currentTime,
                                            int size){
        MonitorNormBuilder builder=new MonitorNormBuilder(currentTime, MonitorTimeUnitEnum.MINUTE,size);
        Query query = new Query();
        query.addCriteria(Criteria.where("host").is(host));
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").gte(builder.getBeforeTime()).lt(currentTime));
        List<IntegratorNormRecord> list=this.find(collectionName,query,IntegratorNormRecord.class);
        return builder.build(list,methodName);
    }

}
