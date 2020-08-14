package com.m.monitor.me.admin.controller;

import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.databoxes.DataBox;
import com.m.beyond.view.page.widgets.Widget;
import com.m.beyond.view.vt.VTEngine;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public  String index(Model model) {
        DataBox dataBox=new DataBox();
        dataBox.setText("你好");
        model.addAttribute("dataBox",dataBox.toHtml());

        Widget widget=new Widget();
        RealTimeLineChart chart=new RealTimeLineChart("0001");
        widget.setBody(chart.toHtml());
        model.addAttribute("widget",widget.toHtml());
        return "index";
    }
}
