package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.Tab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ServerRealTimeWidget {
    private String serverIp;
    private String color;
    private Widget widget=new Widget();
    public ServerRealTimeWidget(String serverIp,String color){
        this.serverIp=serverIp;
        this.color=color;
        widget.setHeadTitle(serverIp);
        initBody(color);
    }
    private void initBody(String color){
        Map<String,String> datas=new HashMap<>();
        datas.put("sys_name","$('#sys_name').val()");//$('#sys.name').val()
        datas.put("point_method","$('#point_method').val()");
        datas.put("server_host","'"+serverIp+"'");
        widget.addRow(new MainRow().add(new RealTimeLineChart(color,new AjaxData("/real_time/data",datas))));

        Tab timeTabs=new Tab(new AjaxData("/p/time_tab",datas));
        timeTabs.add(new TabPane("Last 1 hour",new RealTimeVisitorsChart(color)));
        timeTabs.add(new TabPane("Last 1 day",new RealTimeVisitorsChart(color)));
        timeTabs.add(new TabPane("Last 1 month",new RealTimeVisitorsChart(color)));
        widget.addRow(new MainRow().add(timeTabs));
    }
}
