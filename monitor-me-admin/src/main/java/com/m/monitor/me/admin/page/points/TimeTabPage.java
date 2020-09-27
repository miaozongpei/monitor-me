package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimeTabPage extends BasePage {
    @Override
    public PageHtml create(HttpServletRequest request) {
        String tabPaneType=request.getParameter("tabPane_type");
        String host=request.getParameter("server_host");
        Map<String,String> datas=new HashMap<>();
        datas.put("sys_name","$('#sys_name').val()");//$('#sys.name').val()
        datas.put("point_method","$('#point_method').val()");
        datas.put("global_d_time","$(\"input[name='global_d_time']\")[0].value ");
        datas.put("server_host","'"+host+"'");
        datas.put("tabPane_type","'"+tabPaneType+"'");

        RealTimeVisitorsChart realTimeVisitorsChart=new RealTimeVisitorsChart(Beyond.COLORS.get(1),
                new AjaxData("/real_time/data_visitors",datas));
        realTimeVisitorsChart.setVisitorsChartRangesMinId(host);
        return new PageHtml(realTimeVisitorsChart.toHtml());
    }
}
