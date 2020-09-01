package com.m.monitor.me.admin.page;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/p")
public class PageController {

    @RequestMapping("/html")
    @ResponseBody
    public PageHtml html() {
        PageHtml html=new PageHtml();
        html.setHtml(new RealTimeVisitorsChart(Beyond.colors.get(0)).toHtml());
        return html;
    }
}
