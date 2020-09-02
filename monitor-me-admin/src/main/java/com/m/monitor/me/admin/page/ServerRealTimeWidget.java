package com.m.monitor.me.admin.page;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.charts.BarChart;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.Tab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import com.m.beyond.view.vt.VTEngine;
import lombok.Getter;
import lombok.Setter;

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
        widget.addRow(new MainRow().add(new RealTimeLineChart(color)));
        Tab timeTab=new Tab();
        timeTab.add(new TabPane("Last 1 hour",new RealTimeVisitorsChart(color)));
        timeTab.add(new TabPane("Last 1 day",new RealTimeVisitorsChart(color)));
        timeTab.add(new TabPane("Last 1 month",new RealTimeVisitorsChart(color)));
        widget.addRow(new MainRow().add(timeTab));
    }
}
