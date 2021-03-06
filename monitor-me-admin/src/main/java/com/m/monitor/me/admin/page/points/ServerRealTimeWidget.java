package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.BelowTab;
import com.m.beyond.view.page.tabs.Tab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
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
        widget.setWidgetIcon(Beyond.ICONS.get(1));
        widget.setDispose(true);
        widget.setWarning(true);
        widget.setHeadColor(Beyond.BG_COLORS.get(26));
        initBody(color);
    }
    private void initBody(String color){
        Map<String,String> datas=new HashMap<>();
        datas.put("norm_type","$('#norm_type').val()");
        datas.put("sys_name","$('#sys_name').val()");//$('#sys.name').val()
        datas.put("point_method","$('#point_method').val()");
        //$("input[name='mobile']")[0].value
        datas.put("global_d_time","$(\"input[name='global_d_time']\")[0].value ");
        datas.put("ranges_min_time","$(\"[id='visitors-chart-ranges-min-"+serverIp+"']\").val()");
        datas.put("server_host","'"+serverIp+"'");
        datas.put("warning_id","'"+this.getWidget().getId()+"'");
        widget.addRow(new MainRow().add(new RealTimeLineChart(color,new AjaxData("/real_time/data",datas))));

        BelowTab timeTabs=new BelowTab(new AjaxData("/p/time_tab",datas));
        timeTabs.add(new TabPane("Last 1h", MonitorTimeUnitEnum.HOUR.name(),null));
        timeTabs.add(new TabPane("Last 6h", MonitorTimeUnitEnum.HOUR_6.name(),null));
        timeTabs.add(new TabPane("Last 12h", MonitorTimeUnitEnum.HOUR_12.name(),null));
        timeTabs.add(new TabPane("Last 24h", MonitorTimeUnitEnum.HOUR_12.name(),null));
        timeTabs.add(new TabPane("Last 7d",MonitorTimeUnitEnum.DAY_7.name(),null));
        timeTabs.add(new TabPane("Last 30d",MonitorTimeUnitEnum.DAY_30.name(),null));
        widget.addRow(new MainRow().add(timeTabs));
    }
}
