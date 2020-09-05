package com.m.monitor.me.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.m.monitor.me.service.mogodb.norm.NormMinuteService;
import com.m.monitor.me.service.mogodb.norm.NormSecondService;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/real_time")
public class RealTimeController {
    @Resource
    private NormMinuteService normMinuteService;
    @Resource
    private NormSecondService normSecondService;

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

    @RequestMapping("/dataByMinute")
    @ResponseBody
    public List<double[]> dataByMinute(HttpServletRequest request) {
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        long currentTime=Long.parseLong(DateUtil.formatSecond(new Date().getTime()));
        currentTime=MonitorTimeUtil.subTime(currentTime,1,MonitorTimeUnitEnum.MINUTE);
        method=StringUtils.isEmpty(method)||"all".equals(method)?null:method;
        return normMinuteService.queryRealTimeNorm(name,host, method,
                currentTime, 60);
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
