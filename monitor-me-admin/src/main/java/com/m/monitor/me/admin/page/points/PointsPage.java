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
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PointsPage extends BasePage {
    @Resource
    private MonitorPointService monitorPointService;
    @Resource
    private RealTimeWidgetFactory realTimeWidgetFactory;
    @Override
    public PageHtml create(HttpServletRequest request) {
        String host=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        MainBody mainBody=new MainBody();
        //dataBoxRow
        MainRow dataBoxRow=new MainRow();
        List<String> names=monitorPointService.queryNames();
        dataBoxRow.add(new HalvedDataBox("Applications",names.size(), Beyond.bgColors.get(1),Beyond.iconFas.get(0)));
        List<String> hosts=monitorPointService.queryHosts();
        dataBoxRow.add(new HalvedDataBox("Servers",hosts.size(),Beyond.bgColors.get(2),Beyond.iconFas.get(1)));
        List<String> methods=monitorPointService.queryMethods();
        dataBoxRow.add(new HalvedDataBox("Method Points",methods.size(),Beyond.bgColors.get(3),Beyond.iconFas.get(2)));
        mainBody.add(dataBoxRow);

        mainBody.add(new MainRow().add(new ClearRealtimeInterval()));//清除之前定时
        mainBody.add(new MainRow().add(realTimeWidgetFactory.create("Server Points",host,method)));

        return new PageHtml(mainBody.toHtml());
    }
}