package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.divs.SearchDiv;
import com.m.beyond.view.page.forms.checkboxes.AbstactCheckBox;
import com.m.beyond.view.page.forms.checkboxes.CheckBox;
import com.m.beyond.view.page.forms.inputs.AbstractInput;
import com.m.beyond.view.page.forms.inputs.IconInput;
import com.m.beyond.view.page.functions.AbstractFunction;
import com.m.beyond.view.page.functions.ClearRealtimeInterval;
import com.m.beyond.view.page.functions.ToHtml;
import com.m.beyond.view.page.labels.IconLabel;
import com.m.beyond.view.page.labels.Label;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.LeftTab;
import com.m.beyond.view.page.tabs.Tab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.transfer.norm.SlowMonitorPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LimitPage extends BasePage {
    @Resource
    private MonitorPointService monitorPointService;
    @Override
    public PageHtml create(HttpServletRequest request) {
        String searchKey=request.getParameter("search_key");
        String limited=request.getParameter("limited");

        MainBody mainBody=new MainBody();
        mainBody.add(new MainRow().add(new ClearRealtimeInterval()));//清除之前定时

        Widget limitWidget=new Widget("Limit");
        limitWidget.setHeadBottom(true);





        //search
        SearchDiv searchDiv=new SearchDiv();
        MainRow searchRow=new MainRow();
        //searchInput
        AbstractInput searchInput= new IconInput("search_key",searchKey);
        Map<String,String> datas=new HashMap<>();
        datas.put("search_key","$(\"input[name='search_key']\").val()");
        datas.put("limited","$(\"input[name='limited']:checked\").val()");
        AbstractFunction bindOnchangeFunction=new ToHtml(new AjaxData("p/limit",datas),"page-body");
        searchInput.setBindOnchangeFunction(bindOnchangeFunction.toHtml());
        searchDiv.addRow(new MainRow().add(searchInput));

        //limitedCheckBox
        boolean isLimited="limited".equals(limited);
        AbstactCheckBox limitedCheckBox=new CheckBox("limited","limited","limited", isLimited);
        limitedCheckBox.setBindOnchangeFunction(bindOnchangeFunction.toHtml());

        searchDiv.addRow(new MainRow().add(limitedCheckBox));

        mainBody.add(new MainRow().add(searchDiv));



        Tab methodTabs=new LeftTab(new AjaxData("/p/method_limit_setting",null));
        List<SlowMonitorPoint> slows=monitorPointService.querySlow(searchKey,isLimited,20);
        for(SlowMonitorPoint slow:slows) {
            String bgColor=slow.getAvgNorm()>500? Beyond.BG_COLORS.get(30):Beyond.BG_COLORS.get(26);
            String name=slow.getName();
            String method=slow.getM();
            long mlCount=monitorPointService.queryPointLimitCount(name,null,method);
            String titleLabelText=slow.getAvgNorm().toString()+(mlCount > 0 ? " limited":"");
            Label titleLabel=new IconLabel(Beyond.ICONS.get(4),bgColor,titleLabelText);
            methodTabs.add(new TabPane(method,titleLabel,name, null));
        }
        mainBody.add(new MainRow().add(methodTabs));

        //mainBody.add(new MainRow().add(limitWidget));
        return new PageHtml(mainBody.toHtml());
    }
}