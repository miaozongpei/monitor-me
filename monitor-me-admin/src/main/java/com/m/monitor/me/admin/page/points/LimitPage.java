package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.accordions.Accordion;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.databoxes.HalvedDataBox;
import com.m.beyond.view.page.divs.SearchDiv;
import com.m.beyond.view.page.forms.IconInput;
import com.m.beyond.view.page.functions.ClearRealtimeInterval;
import com.m.beyond.view.page.lists.MoreList;
import com.m.beyond.view.page.lists.TasksList;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.BelowTab;
import com.m.beyond.view.page.tabs.LeftTab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.transfer.norm.SlowMonitorPoint;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
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
        MainBody mainBody=new MainBody();
        mainBody.add(new MainRow().add(new ClearRealtimeInterval()));//清除之前定时

        Widget limitWidget=new Widget("Limit");
        limitWidget.setHeadBottom(true);


        SearchDiv searchDiv=new SearchDiv();
        searchDiv.addRow(new MainRow().add(new IconInput()));
        limitWidget.addRow(new MainRow().add(searchDiv));

        LeftTab methodTabs=new LeftTab(new AjaxData("/p/method_limit_setting",null));
        List<SlowMonitorPoint> slows=monitorPointService.querySlow(20);
        for(SlowMonitorPoint slow:slows) {
            methodTabs.add(new TabPane(slow.getM(),slow.getName(), null));
        }
        limitWidget.addRow(new MainRow().add(methodTabs));

        mainBody.add(new MainRow().add(limitWidget));
        return new PageHtml(mainBody.toHtml());
    }
}
