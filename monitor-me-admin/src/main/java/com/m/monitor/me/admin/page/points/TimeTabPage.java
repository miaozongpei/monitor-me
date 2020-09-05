package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class TimeTabPage extends BasePage {
    @Override
    public PageHtml create(HttpServletRequest request) {
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String host=request.getParameter("server_host");
        RealTimeVisitorsChart realTimeVisitorsChart=new RealTimeVisitorsChart(Beyond.colors.get(1));
        return new PageHtml(realTimeVisitorsChart.toHtml());
    }
}
