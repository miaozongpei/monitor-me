package com.m.monitor.me.admin.page;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.widgets.Widget;
import com.m.beyond.view.vt.VTEngine;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServerRealTimeWidget {
    private String serverIp;
    private String id;
    private Widget widget=new Widget();
    public ServerRealTimeWidget(String id,String serverIp,String color){
        this.id=id;
        this.serverIp=serverIp;
        widget.setHeadTitle(serverIp);
        initBody(color);
    }
    private void initBody(String color){
        MainBody body=new MainBody();
        body.add(new MainRow().add(new RealTimeLineChart(id,color)));
        body.add(new MainRow().add(new RealTimeVisitorsChart(id, color)));
        widget.setBody(body);
    }
}
