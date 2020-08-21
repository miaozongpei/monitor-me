package com.m.monitor.me.admin.controller;

import com.m.monitor.me.service.mogodb.norm.MethodPointService;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/real_time")
public class RealTimeController {
    @Resource
    private MethodPointService methodPointService;
    @RequestMapping("/data")
    @ResponseBody
    public List<double[]> data(Model model) {

        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        return methodPointService.queryRealTimeNorm("10.249.243.155", null,
                MonitorTimeUtil.subTime(currentTime,1,MonitorTimeUnitEnum.MINUTE),
                MonitorTimeUnitEnum.SECOND,60);
    }

    @RequestMapping("/dataByMinute")
    @ResponseBody
    public List<double[]> dataByMinute(Model model) {
        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        return methodPointService.queryRealTimeNorm("10.249.243.155", null,
                currentTime,
                MonitorTimeUnitEnum.SECOND,60*60);
    }
    @RequestMapping("/data1")
    @ResponseBody
    public List<Double[]> data1(Model model) {
        List<Double[]> data=new ArrayList<>();
        for (int i=0;i<60;i++) {
            data.add(new Double[]{Double.parseDouble(new Date().getTime() + i*1000+""), Math.random()*3000});
        }
        return data;
    }
}
