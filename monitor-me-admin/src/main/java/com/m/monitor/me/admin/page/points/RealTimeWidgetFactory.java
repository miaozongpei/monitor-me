package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.BarChart;
import com.m.beyond.view.page.forms.ComboSelect;
import com.m.beyond.view.page.forms.SelectOption;
import com.m.beyond.view.page.functions.ToHtml;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RealTimeWidgetFactory {
    @Resource
    private MonitorPointService monitorPointService;

    public Widget create(String title,String defaultName,String defaultMethod){
        Widget widget=new Widget();

        widget.setHeadTitle(title);
        widget.setHeadBottom(true);

        //ToHtml
        Map<String,String> datas=new HashMap<>();
        datas.put("sys_name","$('#sys_name').val()");//$('#sys.name').val()
        datas.put("point_method","$('#point_method').val()");
        ToHtml bindOnchangeFunction=new ToHtml(new AjaxData("p/points",datas),"page-body");

        //search
        MainRow searchRow=new MainRow();
        ComboSelect sysNameSelect=new ComboSelect("sys_name");
        List<String> names=monitorPointService.queryNames();
        for(String name:names){
            sysNameSelect.add(new SelectOption(name, name).setSelected(defaultName));
        }
        sysNameSelect.setBindOnchangeFunction(bindOnchangeFunction.toHtml());
        searchRow.add(sysNameSelect.setLg(3));

        ComboSelect methodSelect =new ComboSelect("point_method");
        methodSelect.add(new SelectOption("all", "all"));
        if (names.size()>0) {
            defaultName = StringUtils.isEmpty(defaultName)?names.get(0):defaultName;
            List<String> methods=monitorPointService.queryMethodsByName(defaultName);
            for (String method:methods) {
                methodSelect.add(new SelectOption(method, method).setSelected(defaultMethod));
            }
            methodSelect.setBindOnchangeFunction(bindOnchangeFunction.toHtml());

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
        Widget barWidget = new Widget("Servers Comparison",new BarChart(new AjaxData("real_time/data_servers_bar",datas)));
        widget.addRow(new MainRow().add(barWidget));

        return widget;
    }
}