package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.transfer.server.builder.MethodNormBuilder;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DoubleUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseNormService extends BaseMongoService<IntegratorNormRecord>{
    public abstract String getCollectionName();
    public abstract MonitorTimeUnitEnum getMonitorTimeUnitEnum();
    public void saveOrModify(IntegratorNormBuilder integratorNormBuilder){
        String name=integratorNormBuilder.getName();
        String host=integratorNormBuilder.getHost();
        IntegratorNormRecord record=integratorNormBuilder.getIntegratorNormRecord(getMonitorTimeUnitEnum());
        if (record!=null){
            List<IntegratorNormRecord> records=record.toList();
            for (IntegratorNormRecord integratorNormRecord:record.toList()){
                saveOrModify(integratorNormBuilder,integratorNormRecord);
            }
        }

    }
    private void saveOrModify(IntegratorNormBuilder integratorNormBuilder,IntegratorNormRecord integratorNormRecord){
        String name=integratorNormRecord.getName();
        String host=integratorNormRecord.getHost();
        Long time=integratorNormRecord.getTs().get(0).getT();
        IntegratorNormRecord current=queryOne(name,host,time);
        if (current==null){
            save(integratorNormRecord);
        }else {
            integratorNormBuilder.putBeforeIntegratorNormRecord(getMonitorTimeUnitEnum(),current);
            TimeNorm targetTimeNorm=integratorNormBuilder.getTimeNormBuilder(time,getMonitorTimeUnitEnum());
            List<TimeNorm> ts=new ArrayList<>();
            ts.add(targetTimeNorm);
            integratorNormRecord.setTs(ts);
            modify(current.getId(),integratorNormRecord);
        }
    }
    public void save(IntegratorNormRecord integratorNormRecord){
        mongoTemplate.insert(integratorNormRecord,getCollectionName());
    }
    public void modify(String id,IntegratorNormRecord update){
        this.update(getCollectionName(),id,update);
    }
    public IntegratorNormRecord queryOne(String name,String host,Long time){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        query.addCriteria(Criteria.where("ts.t").is(time));
        return this.mongoTemplate.findOne(query,IntegratorNormRecord.class,getCollectionName());
    }
    public List<double[]> queryRealTimeNorm(String name,String host,
                                            String methodName,
                                            long currentTime,
                                            int size){
        MonitorNormBuilder builder=new MonitorNormBuilder(currentTime, getMonitorTimeUnitEnum(),size);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").gte(builder.getBeforeTime()).lt(currentTime));
        List<IntegratorNormRecord> list=this.find(getCollectionName(),query,IntegratorNormRecord.class);
        return builder.build(list,methodName);
    }
    public double[] queryRealTimeNorm(String name,String host,
                                            String methodName,
                                            long currentTime){
        MonitorNormBuilder builder=new MonitorNormBuilder(currentTime, getMonitorTimeUnitEnum(),1);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("host").is(host));
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").is(currentTime));
        List<IntegratorNormRecord> list=this.find(getCollectionName(),query,IntegratorNormRecord.class);
        List<double[]> result= builder.build(list,methodName);
        return CollectionUtils.isEmpty(result)?new double[]{Double.parseDouble(currentTime+""),0D}:result.get(0);
    }

}
