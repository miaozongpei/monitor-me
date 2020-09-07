package com.m.monitor.me.admin.controller;

import com.m.beyond.view.data.charts.BarchartData;
import com.m.monitor.me.service.mogodb.norm.*;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
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
    private MonitorPointService monitorPointService;

    @RequestMapping("/data")
    @ResponseBody
    public List<double[]> data(HttpServletRequest request) {
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        currentTime=MonitorTimeUtil.subTime(currentTime,1,MonitorTimeUnitEnum.MINUTE);
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        return normSecondService.queryRealTimeNorm(name,host,method,
                currentTime,60);
    }

    @RequestMapping("/data_visitors")
    @ResponseBody
    public List<double[]> dataVisitors(HttpServletRequest request) {
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        String tabPaneType=request.getParameter("tabPane_type");

        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        currentTime=MonitorTimeUtil.subTime(currentTime,1,MonitorTimeUnitEnum.MINUTE);
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        if (MonitorTimeUnitEnum.HOUR.name().equals(tabPaneType)) {
            return normMinuteService.queryRealTimeNorm(name, host, method,
                    currentTime, 60);
        }else if (MonitorTimeUnitEnum.DAY.name().equals(tabPaneType)) {
            return normHourService.queryRealTimeNorm(name, host, method,
                    currentTime, 24);
        }else if (MonitorTimeUnitEnum.MONTH.name().equals(tabPaneType)) {
            return normDayService.queryRealTimeNorm(name, host, method,
                    currentTime, 30);
        }else {
            return null;
        }
    }
    @RequestMapping("/data_servers_bar")
    @ResponseBody
    public BarchartData dataServerBar(HttpServletRequest request) {
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        List<String> hosts=monitorPointService.queryHosts();
        BarchartData data=new BarchartData();
        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        currentTime=MonitorTimeUtil.toTime(currentTime,MonitorTimeUnitEnum.DAY);
        for(int i=0;i<10;i++) {
            Map<String, Double> yData = new HashMap<>();
            long time=MonitorTimeUtil.subTime(currentTime,i,MonitorTimeUnitEnum.DAY);
            for (String host : hosts) {
                double[] norm=normDayService.queryRealTimeNorm(name,host,method,time);
                yData.put(host, norm[1]);
            }
            data.putData("date",Long.toString(time).substring(0,8),yData);
        }

        return data;

    }
}
