package com.m.monitor.me.admin.controller;

import com.m.beyond.view.data.charts.BarchartData;
import com.m.monitor.me.service.mogodb.service.*;
import com.m.monitor.me.service.mogodb.service.norm.NormDayService;
import com.m.monitor.me.service.mogodb.service.norm.NormHourService;
import com.m.monitor.me.service.mogodb.service.norm.NormMinuteService;
import com.m.monitor.me.service.mogodb.service.norm.NormSecondService;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/real_time")
@Slf4j
public class RealTimeController {
    @Resource
    private NormMinuteService normMinuteService;
    @Resource
    private NormSecondService normSecondService;

    @Resource
    private NormHourService normHourService;

    @Resource
    private NormDayService normDayService;
    @Resource
    private MonitorHostService monitorHostService;


    @RequestMapping("/data")
    @ResponseBody
    public List<double[]> data(HttpServletRequest request) {
        String type=request.getParameter("norm_type");
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        String globalDTime=request.getParameter("global_d_time");


        String rangesMinTime=request.getParameter("ranges_min_time");
        long time=new Date().getTime()-(StringUtils.isEmpty(globalDTime)||"NaN".equals(globalDTime)?0L:Long.parseLong(globalDTime));
        long currentTime = Long.parseLong(DateUtil.formatSecond(time));
        log.info("[data] currentTime:{}",currentTime);
        if (!StringUtils.isEmpty(rangesMinTime)) {
            String[] rangesTimes=rangesMinTime.split("-");
            long sTime=Long.parseLong(rangesTimes[1]);
            Double rangesTime=Double.parseDouble(rangesTimes[0]);
            long rangesMinTimeLong=rangesTime.longValue()+(time-sTime);
            Date rangesMinDate=new Date(rangesMinTimeLong);
            currentTime=Long.parseLong(DateUtil.formatSecond(rangesMinDate.getTime()));
        }else {
            currentTime = MonitorTimeUtil.subTime(currentTime, 15, MonitorTimeUnitEnum.SECOND);
        }
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        return normSecondService.queryRealTimeNorm(type,name,host,method,
                currentTime,60);
    }

    @RequestMapping("/data_visitors")
    @ResponseBody
    public List<double[]> dataVisitors(HttpServletRequest request) {
        String type=request.getParameter("norm_type");
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        String tabPaneType=request.getParameter("tabPane_type");
        String globalDTime=request.getParameter("global_d_time");

        long time=System.currentTimeMillis()-(StringUtils.isEmpty(globalDTime)?0L:Long.parseLong(globalDTime));
        long currentTime = Long.parseLong(DateUtil.formatSecond(time));
        currentTime=MonitorTimeUtil.subTime(currentTime,1,MonitorTimeUnitEnum.MINUTE);
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        if (MonitorTimeUnitEnum.HOUR.name().equals(tabPaneType)) {
            return normMinuteService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 60);
        }else if (MonitorTimeUnitEnum.HOUR_6.name().equals(tabPaneType)) {
            return normMinuteService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 6*60);
        }else if (MonitorTimeUnitEnum.HOUR_12.name().equals(tabPaneType)) {
            return normMinuteService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 12*60);
        }else if (MonitorTimeUnitEnum.HOUR_24.name().equals(tabPaneType)) {
            return normMinuteService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 24*60);
        }else if (MonitorTimeUnitEnum.DAY_7.name().equals(tabPaneType)) {
            return normHourService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 7*24);
        }else if (MonitorTimeUnitEnum.DAY_30.name().equals(tabPaneType)) {
            return normHourService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 30*24);
        }else if (MonitorTimeUnitEnum.MONTH.name().equals(tabPaneType)) {
            return normDayService.queryRealTimeNorm(type,name, host, method,
                    currentTime, 30);
        }else {
            return null;
        }
    }
    @RequestMapping("/data_servers_bar")
    @ResponseBody
    public BarchartData dataServerBar(HttpServletRequest request) {
        String type=request.getParameter("norm_type");
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        List<String> hosts=monitorHostService.queryHostsByName(name);
        BarchartData data=new BarchartData();
        String globalDTime=request.getParameter("global_d_time");

        long dTime=System.currentTimeMillis()-(StringUtils.isEmpty(globalDTime)?0L:Long.parseLong(globalDTime));
        long currentTime = Long.parseLong(DateUtil.formatSecond(dTime));
        currentTime=MonitorTimeUtil.toTime(currentTime,MonitorTimeUnitEnum.DAY);
        for(int i=0;i<10;i++) {
            Map<String, Double> yData = new HashMap<>();
            long time=MonitorTimeUtil.subTime(currentTime,i,MonitorTimeUnitEnum.DAY);
            for (String host : hosts) {
                double[] norm=normDayService.queryRealTimeNorm(type,name,host,method,time);
                yData.put(host, norm[1]);
            }
            data.putData("date",Long.toString(time).substring(0,8),yData);
        }

        return data;

    }
}
