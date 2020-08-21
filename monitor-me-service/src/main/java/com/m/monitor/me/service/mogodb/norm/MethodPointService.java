package com.m.monitor.me.service.mogodb.norm;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.task.IntegratorRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
@Component
public class MethodPointService extends BaseMongoService<IntegratorRecord> {

    public List<double[]> queryRealTimeNorm(String host,
                                            String methodName,
                                            long currentTime,
                                            MonitorTimeUnitEnum timeUnit,
                                            int size){
        MonitorNormBuilder builder=new MonitorNormBuilder(currentTime,timeUnit,size);
        Query query = new Query();
        query.addCriteria(Criteria.where("host").is(host));
        if (!StringUtils.isEmpty(methodName)) {
            query.addCriteria(Criteria.where("ts.ms.m").is(methodName));
        }
        query.addCriteria(Criteria.where("ts.t").gte(builder.getBeforeTime()).lt(currentTime));
        List<IntegratorRecord> list=this.find(getCollectName(),query,IntegratorRecord.class);
        return builder.build(list,methodName);
    }

    public String getCollectName(){
        return DateUtil.format(new Date(),DateUtil.FORMAT_YYYYMM);
    }

}
