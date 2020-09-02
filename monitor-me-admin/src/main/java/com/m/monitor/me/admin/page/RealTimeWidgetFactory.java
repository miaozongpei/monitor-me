package com.m.monitor.me.admin.page;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.charts.BarChart;
import com.m.beyond.view.page.forms.ComboSelect;
import com.m.beyond.view.page.forms.SelectOption;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
@Component
public class RealTimeWidgetFactory {
    @Resource
    private MonitorPointService monitorPointService;

    public Widget create(String title){
        Widget widget=new Widget();

        widget.setHeadTitle(title);
        widget.setHeadBottom(true);
        //search
        MainRow searchRow=new MainRow();
        ComboSelect sysNameSelect=new ComboSelect("sysName");
        List<String> names=monitorPointService.queryNames();
        for(String name:names){
            sysNameSelect.add(new SelectOption(name, name));
        }
        searchRow.add(sysNameSelect.setLg(3));

        ComboSelect methodSelect =new ComboSelect("method");
        methodSelect.add(new SelectOption("all", "all"));
        if (names.size()>0) {
            String defaultName =names.get(0);
            List<String> methods=monitorPointService.queryMethodsByName(defaultName);
            for (String method:methods) {
                methodSelect.add(new SelectOption(method, method));
            }
            searchRow.add(methodSelect);
            widget.addRow(searchRow);

            //ServerRealTimeWidget
            List<String> hosts=monitorPointService.queryHostsByName(defaultName);
            MainRow realTimeLineChartRow = new MainRow();
            for (int i=0;i<hosts.size();i++){
                realTimeLineChartRow.add(new ServerRealTimeWidget(hosts.get(i), Beyond.colors.get(1)).getWidget());
                if ((i+1)%2==0) {
                    widget.addRow(realTimeLineChartRow);
                    realTimeLineChartRow= new MainRow();
                }
            }
            if (realTimeLineChartRow.getElements().size()!=0) {
                realTimeLineChartRow.add(new MainRow());
                widget.addRow(realTimeLineChartRow);
            }
        }

        //访问量
        Widget barWidget = new Widget("访问量",new BarChart());
        widget.addRow(new MainRow().add(barWidget));

        return widget;
    }
}
