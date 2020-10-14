package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.BelowTab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.enums.QueryNormTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ServerRealTimeLimitWidget {
    private String serverIp;
    private String sysName;
    private String pointMethod;
    private Widget widget=new Widget();
    public ServerRealTimeLimitWidget(String serverIp,String sysName,String pointMethod,String exTitle){
        this.serverIp=serverIp;
        this.sysName=sysName;
        this.pointMethod=pointMethod;
        String headTitle="host="+serverIp+", "+exTitle;
        widget.setHeadTitle(headTitle);
        widget.setWarning(true);

        initBody();
    }
    private void initBody(){
        Map<String,String> datas=new HashMap<>();
        datas.put("server_host","'"+serverIp+"'");
        datas.put("norm_type","'"+ QueryNormTypeEnum.RT.name() +"'");
        datas.put("sys_name","'"+sysName+"'");
        datas.put("point_method","'"+pointMethod+"'");
        datas.put("warning_id","'"+this.getWidget().getId()+"'");

        MainRow row=new MainRow();
        row.add(new RealTimeLineChart(Beyond.COLORS.get(1),new AjaxData("/real_time/data",datas)));

        datas.put("norm_type","'"+ QueryNormTypeEnum.TP.name() +"'");
        row.add(new RealTimeLineChart(Beyond.COLORS.get(4),new AjaxData("/real_time/data",datas)));

        widget.addRow(row);
    }
}
