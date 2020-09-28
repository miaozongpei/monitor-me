package com.m.monitor.me.admin.page.points;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.databoxes.HalvedDataBox;
import com.m.beyond.view.page.functions.ClearRealtimeInterval;
import com.m.beyond.view.page.functions.ToHtml;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.service.mogodb.norm.MonitorHostService;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PointsPage extends BasePage {
    @Resource
    private MonitorPointService monitorPointService;
    @Resource
    private RealTimeWidgetFactory realTimeWidgetFactory;

    @Resource
    private MonitorHostService monitorHostService;
    @Override
    public PageHtml create(HttpServletRequest request) {
        String host=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        MainBody mainBody=new MainBody();
        //dataBoxRow
        MainRow dataBoxRow=new MainRow();
        List<String> names=monitorHostService.queryNames();
        dataBoxRow.add(new HalvedDataBox("Applications",names.size(), Beyond.BG_COLORS.get(1),Beyond.ICON_FAS.get(0)));
        List<String> hosts=monitorHostService.queryHosts();
        dataBoxRow.add(new HalvedDataBox("Servers",hosts.size(),Beyond.BG_COLORS.get(2),Beyond.ICON_FAS.get(1)));
        List<String> methods=monitorPointService.queryMethods();
        dataBoxRow.add(new HalvedDataBox("Method Points",methods.size(),Beyond.BG_COLORS.get(3),Beyond.ICON_FAS.get(2)));
        mainBody.add(dataBoxRow);
        mainBody.add(new MainRow().add(new ClearRealtimeInterval()));//清除之前定时
        mainBody.add(new MainRow().add(realTimeWidgetFactory.create("Server Points",host,method)));
        return new PageHtml(mainBody.toHtml());
    }
}
