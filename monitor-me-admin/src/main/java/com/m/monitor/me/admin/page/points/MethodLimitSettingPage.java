package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.accordions.AccordionItem;
import com.m.beyond.view.page.accordions.CheckBoxAccordion;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.divs.FormGroupDiv;
import com.m.beyond.view.page.forms.inputs.CheckboxSliderInput;
import com.m.beyond.view.page.forms.inputs.HiddenInput;
import com.m.beyond.view.page.forms.inputs.TwoSidedSpinboxInput;
import com.m.beyond.view.page.functions.ClearRealtimeInterval;
import com.m.beyond.view.page.functions.FormAjaxConfirmForResultMsg;
import com.m.beyond.view.page.mains.Hr;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.widgets.FormWidget;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import com.m.monitro.me.common.limit.PointLimit;
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


        /*Map<String,String> datas=new HashMap<>();
        datas.put("tabPane_title","'"+method+"'");
        datas.put("tabPane_type","'"+name+"'");
        RealTimeLineChart serverRealTimeChart=new RealTimeLineChart(Beyond.COLORS.get(1),new AjaxData("/real_time/data_group",datas));
        mainBody.add(new MainRow().add(serverRealTimeChart));*/
        List<MonitorPointRecord> serverChains=monitorPointService.queryList(name,method);
        for (MonitorPointRecord serverChain:serverChains){
            String serverIP=serverChain.getHost();
            mainBody.add(new MainRow().add(new ServerRealTimeLimitWidget(serverIP,name,method,serverChain.getMl().toString()).getWidget()));

        }

        Widget LimitSettingWidget=new FormWidget("Setting",new FormAjaxConfirmForResultMsg("/limit/setting"));

        LimitSettingWidget.setWidgetIcon(Beyond.ICON_FAS.get(3));

        LimitSettingWidget.setHeadColor(Beyond.BG_COLORS.get(1));


        LimitSettingWidget.addRow(new MainRow().add(new HiddenInput("point_method",method)));
        LimitSettingWidget.addRow(new MainRow().add(new HiddenInput("sys_name",name)));


        CheckBoxAccordion hostCheckBoxGroup=new CheckBoxAccordion("hosts");

        PointLimit currentDefaultLimit=new PointLimit();
        for (MonitorPointRecord serverChain:serverChains){
            String bodyTxt=serverChain.getMc().getChain();
            bodyTxt=bodyTxt.replaceAll("\n","<br/>").replaceAll(" ","&nbsp;");
            hostCheckBoxGroup.add(new AccordionItem(serverChain.getHost(),bodyTxt));
            if (serverChain.getMl()!=null){
                currentDefaultLimit=serverChain.getMl();
            }
        }
        LimitSettingWidget.addRow(new MainRow().add(hostCheckBoxGroup));


        LimitSettingWidget.addRow(new MainRow()
                .add(new FormGroupDiv("maxWaiting",new TwoSidedSpinboxInput("waitingThreadMax",currentDefaultLimit.getWaitingThreadMax()+"")))
                .add(new FormGroupDiv("maxTps",new TwoSidedSpinboxInput("tpsMax",currentDefaultLimit.getTpsMax()+"")))
                .add(new FormGroupDiv("sleep",new TwoSidedSpinboxInput("sleepMillis",currentDefaultLimit.getSleepMillis()+"")))

        );
        LimitSettingWidget.addRow(new Hr());




        LimitSettingWidget.addRow(new MainRow()
                .add(new CheckboxSliderInput("breakFlag","1","isBreak",currentDefaultLimit.getBreakFlag()!=0))
        );
        LimitSettingWidget.addRow(new Hr());







        mainBody.add(new MainRow().add(LimitSettingWidget));
        return new PageHtml(mainBody.toHtml());
    }
}
