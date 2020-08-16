package com.m.monitor.me.admin.controller;

import com.m.beyond.view.data.charts.BarchartData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/bar_chart")
public class BarChartController {
    @RequestMapping("/data")
    @ResponseBody
    public BarchartData data(Model model) {
        BarchartData data=new BarchartData();
        for (int j=1;j<8;j++) {
            Map<String, Double> yData = new HashMap<>();
            for (int i = 0; i < 4; i++) {
                yData.put("服务器" + i, Math.random() * 1000);
            }
            data.putData("date","2020080"+j,yData);
        }
        return data;
    }
}
