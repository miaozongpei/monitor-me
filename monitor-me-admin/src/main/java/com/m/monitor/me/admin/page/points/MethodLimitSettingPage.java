package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.accordions.Accordion;
import com.m.beyond.view.page.accordions.AccordionItem;
import com.m.beyond.view.page.accordions.CheckBoxAccordion;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.divs.FormGroupDiv;
import com.m.beyond.view.page.divs.SearchDiv;
import com.m.beyond.view.page.forms.CheckBox;
import com.m.beyond.view.page.forms.CheckboxSlider;
import com.m.beyond.view.page.forms.IconInput;
import com.m.beyond.view.page.forms.spinboxs.TwoSidedSpinbox;
import com.m.beyond.view.page.functions.ClearRealtimeInterval;
import com.m.beyond.view.page.mains.Hr;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.spans.Span;
import com.m.beyond.view.page.tabs.LeftTab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.CheckBoxWidget;
import com.m.beyond.view.page.widgets.FormWidget;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.transfer.norm.SlowMonitorPoint;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MethodLimitSettingPage extends BasePage {
    @Resource
    private MonitorPointService monitorPointService;
    @Override
    public PageHtml create(HttpServletRequest request) {
        MainBody mainBody=new MainBody();
        mainBody.add(new MainRow().add(new ClearRealtimeInterval()));//清除之前定时


        String method=request.getParameter("tabPane_title");
        String name=request.getParameter("tabPane_type");


        Map<String,String> datas=new HashMap<>();
        datas.put("tabPane_title","'"+method+"'");
        datas.put("tabPane_type","'"+name+"'");
        RealTimeLineChart serverRealTimeChart=new RealTimeLineChart(Beyond.COLORS.get(1),new AjaxData("/real_time/data_group",datas));
        mainBody.add(new MainRow().add(serverRealTimeChart));

        Widget LimitSettingWidget=new FormWidget("Setting");
        LimitSettingWidget.setWidgetIcon(Beyond.ICON_FAS.get(3));

        LimitSettingWidget.setHeadColor(Beyond.BG_COLORS.get(1));




        CheckBoxAccordion hostCheckBoxGroup=new CheckBoxAccordion("hosts");
        List<MonitorPointRecord> serverChains=monitorPointService.queryList(name,method);
        for (MonitorPointRecord serverChain:serverChains){
            String bodyTxt=serverChain.getMc().getChain();
            bodyTxt=bodyTxt.replaceAll("\n","<br/>").replaceAll(" ","&nbsp;");
            hostCheckBoxGroup.add(new AccordionItem(serverChain.getHost(),bodyTxt));
        }
        LimitSettingWidget.addRow(new MainRow().add(hostCheckBoxGroup));


        LimitSettingWidget.addRow(new MainRow()
                .add(new FormGroupDiv("maxWaiting",new TwoSidedSpinbox()))
                .add(new FormGroupDiv("maxTps",new TwoSidedSpinbox()))
                .add(new FormGroupDiv("sleep",new TwoSidedSpinbox()))

        );
        LimitSettingWidget.addRow(new Hr());




        LimitSettingWidget.addRow(new MainRow()
                .add(new CheckboxSlider("isBreak"))
        );
        LimitSettingWidget.addRow(new Hr());







        mainBody.add(new MainRow().add(LimitSettingWidget));
        return new PageHtml(mainBody.toHtml());
    }
}
