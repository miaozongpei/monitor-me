package com.m.monitor.me.admin.controller;

import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.databoxes.DataBox;
import com.m.beyond.view.page.widgets.Widget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/real_time")
public class RealTimeController {
    @RequestMapping("/data")
    @ResponseBody
    public List<Double[]> data(Model model) {
        List<Double[]> data=new ArrayList<>();
        for (int i=0;i<60;i++) {
            data.add(new Double[]{Double.parseDouble(new Date().getTime() + i*1000+""), Math.random()*3000});
        }
        return data;
    }
}
