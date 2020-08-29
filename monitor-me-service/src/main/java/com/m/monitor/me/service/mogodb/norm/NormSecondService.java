package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class NormSecondService extends BaseMongoService<IntegratorNormRecord>{
    private String collectionName="norm_second";
    public void save(IntegratorNormRecord integratorNormRecord){
        super.insert(collectionName,integratorNormRecord);
    }
    public List<double[]> queryRealTimeNorm(String host,
                                            String methodName,
                                            long currentTime,
                                            int size){
        MonitorNormBuilder builder=new MonitorNormBuilder(currentTime,MonitorTimeUnitEnum.SECOND,size);
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
