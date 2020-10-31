package com.m.monitor.me.service.mogodb.record;

import com.m.monitor.me.service.transfer.norm.MethodNorm;
import com.m.monitor.me.service.transfer.norm.TimeNorm;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * 聚合指标记录
 * @Author: miaozp
 * @Date: 2020/10/31 3:04 下午
 **/
@Data
public class IntegratorNormRecord {
    private String id;
    private String name;
    private String host;
    private List<TimeNorm> ts=new ArrayList<>();
    public IntegratorNormRecord(String name, String host) {
        this.name = name;
        this.host = host;
    }
    private TimeNorm getTsByTime(Long time){
        for (TimeNorm timeNorm:ts){
            if (timeNorm.getT()==time){
                return timeNorm;
            }
        }
        return null;
    }

    public IntegratorNormRecord add(IntegratorNormRecord normRecord){
        if (name.equals(normRecord.name)&&host.equals(normRecord.host)){
            for (TimeNorm norm:normRecord.getTs()){
                TimeNorm target=this.getTsByTime(norm.getT());
                if (target==null){
                    break;
                }
                for (MethodNorm methodNorm:norm.getMs()){
                    MethodNorm targetMethod=target.getMethodNorm(methodNorm.getM());
                    if (targetMethod==null){
                        target.getMs().add(methodNorm);
                    }else{
                        targetMethod.add(methodNorm);
                    }
                }
                target.cal();
            }
        }

        return this;
    }

    public List<IntegratorNormRecord> toList(){
        List<IntegratorNormRecord> list=new ArrayList<>();
        for (int i=0;i<this.getTs().size();i++){
            IntegratorNormRecord record=new IntegratorNormRecord(name,host);
            TimeNorm timeNorm=this.getTs().get(i);
            timeNorm.cal();
            record.getTs().add(timeNorm);
            list.add(record);
        }
        return list;
    }

}
