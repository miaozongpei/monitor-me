package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.charts.BarChart;
import com.m.beyond.view.page.divs.SearchDiv;
import com.m.beyond.view.page.forms.DateTimePicker;
import com.m.beyond.view.page.forms.Select2;
import com.m.beyond.view.page.forms.SelectOption;
import com.m.beyond.view.page.functions.ToHtml;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.service.mogodb.service.MonitorHostService;
import com.m.monitor.me.service.mogodb.service.MonitorPointService;
import com.m.monitro.me.common.enums.QueryNormTypeEnum;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Component
public class RealTimeWidgetFactory {
    @Resource
    private MonitorPointService monitorPointService;
    @Resource
    private MonitorHostService monitorHostService;
    public Widget create(String title,String defaultType,String defaultName,String defaultMethod){
        Widget widget=new Widget();

        widget.setHeadTitle(title);
        widget.setHeadBottom(true);

        //ToHtml
        Map<String,String> datas=new HashMap<>();
        datas.put("norm_type","$('#norm_type').val()");
        datas.put("sys_name","$('#sys_name').val()");
        datas.put("point_method","$('#point_method').val()");
        datas.put("global_d_time","$(\"input[name='global_d_time']\")[0].value ");
        ToHtml bindOnchangeFunction=new ToHtml(new AjaxData("p/points",datas),"page-body");

        //search
        SearchDiv searchDiv=new SearchDiv();
        MainRow searchRow=new MainRow();
        //norm_type
        Select2 normTypeSelect=new Select2("norm_type");
        normTypeSelect.add(new SelectOption(QueryNormTypeEnum.RT.name(), QueryNormTypeEnum.RT.name()).setSelected(defaultType));
        normTypeSelect.add(new SelectOption(QueryNormTypeEnum.TP.name(), QueryNormTypeEnum.TP.name()).setSelected(defaultType));
        normTypeSelect.setBindOnchangeFunction(bindOnchangeFunction.toHtml());
        searchRow.add(normTypeSelect);

        //sys_name
        Select2 sysNameSelect=new Select2("sys_name");
        List<String> names=monitorHostService.queryNames();
        for(String name:names){
            sysNameSelect.add(new SelectOption(name, name).setSelected(defaultName));
        }
        sysNameSelect.setBindOnchangeFunction(bindOnchangeFunction.toHtml());
        searchRow.add(sysNameSelect);

        //point_method
        Select2 methodSelect =new Select2("point_method");
        methodSelect.add(new SelectOption("all", "all"));
        if (names.size()>0) {
            defaultName = StringUtils.isEmpty(defaultName)?names.get(0):defaultName;
            List<String> methods=monitorPointService.queryMethodsByName(defaultName);
            for (String method:methods) {
                methodSelect.add(new SelectOption(method, method).setSelected(defaultMethod));
            }
            methodSelect.setBindOnchangeFunction(bindOnchangeFunction.toHtml());
            searchRow.add(methodSelect);

            //global_d_time
            DateTimePicker dateTimePicker=new DateTimePicker("global_d_time");
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.HOUR, 6);
            dateTimePicker.setDefaultDate(DateFormatUtils.ISO_DATE_FORMAT.format(calendar));
            dateTimePicker.setDefaultTime(DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(calendar));

            searchRow.add(dateTimePicker);

            searchDiv.addRow(searchRow);
            widget.addRow(new MainRow().add(searchDiv));

            //ServerRealTimeWidget
            List<String> hosts=monitorHostService.queryHostsByName(defaultName);
            MainRow realTimeLineChartRow = new MainRow();
            for (int i=0;i<hosts.size();i++){
                realTimeLineChartRow.add(new ServerRealTimeWidget(hosts.get(i), Beyond.COLORS.get(1)).getWidget());
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
