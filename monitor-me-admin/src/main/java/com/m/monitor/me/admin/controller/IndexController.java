package com.m.monitor.me.admin.controller;

import com.m.beyond.view.page.charts.BarChart;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.charts.RealTimeVisitorsChart;
import com.m.beyond.view.page.databoxes.DataBox;
import com.m.beyond.view.page.loadings.LoadingContainer;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainHeader;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.menus.SidebarMenu;
import com.m.beyond.view.page.navbars.NavBar;
import com.m.beyond.view.page.widgets.Widget;
import com.m.monitor.me.admin.page.ServerRealTimeWidget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public  String index(Model model) {
        NavBar navBar=new NavBar("Monitor Me");
        model.addAttribute("navBar",navBar.toHtml());

        SidebarMenu menu=new SidebarMenu();
        model.addAttribute("menu",menu.toHtml());

        LoadingContainer loadingContainer=new LoadingContainer();
        model.addAttribute("loadingContainer",loadingContainer);

        MainHeader mainHeader=new MainHeader();
        model.addAttribute("mainHeader",mainHeader.toHtml());



        MainBody mainBody=new MainBody();

        //dataBoxRow
        MainRow dataBoxRow=new MainRow();
        dataBoxRow.add(new DataBox("系统数量"));
        dataBoxRow.add(new DataBox("服务器数量"));
        mainBody.add(dataBoxRow);

        //realTimeLineChartRow
        MainRow realTimeLineChartRow=new MainRow();
        for(int i=0;i<2;i++) {
            ServerRealTimeWidget widget = new ServerRealTimeWidget(""+i,"服务器xx"+i);
            realTimeLineChartRow.add(widget.getWidget());
        }

        //realTimeLineChartRow
        MainRow realTimeLineChartRow1=new MainRow();
        for(int i=0;i<2;i++) {
            ServerRealTimeWidget widget = new ServerRealTimeWidget("xx"+i,"服务器xx"+i);
            realTimeLineChartRow1.add(widget.getWidget());
        }

        //realTimeWidget
        MainBody realTimeWidgetBody=new MainBody();
        realTimeWidgetBody.add(realTimeLineChartRow);
        realTimeWidgetBody.add(realTimeLineChartRow1);
        Widget realTimeWidget = new Widget("Real-Time",realTimeWidgetBody);
        realTimeWidget.setHeadBottom(true);
        mainBody.add(new MainRow().add(realTimeWidget));

        //访问量
        Widget barWidget = new Widget("访问量",new BarChart("0"));
        mainBody.add(new MainRow().add(barWidget));




        model.addAttribute("mainBody",mainBody.toHtml());

        return "index";
    }
}
