package com.m.monitor.me.admin.controller;

import com.m.beyond.view.Beyond;
import com.m.beyond.view.page.databoxes.DataBox;
import com.m.beyond.view.page.databoxes.HalvedDataBox;
import com.m.beyond.view.page.loadings.LoadingContainer;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainHeader;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.menus.SidebarMenu;
import com.m.beyond.view.page.navbars.NavBar;
import com.m.monitor.me.admin.page.RealTimeWidgetFactory;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private MonitorPointService monitorPointService;
    @Resource
    private RealTimeWidgetFactory realTimeWidgetFactory;
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
        List<String> names=monitorPointService.queryNames();

        dataBoxRow.add(new HalvedDataBox("Applications",names.size(),Beyond.bgColors.get(1),Beyond.iconFas.get(0)));
        List<String> hosts=monitorPointService.queryHosts();
        dataBoxRow.add(new HalvedDataBox("Servers",hosts.size(),Beyond.bgColors.get(2),Beyond.iconFas.get(1)));
        List<String> methods=monitorPointService.queryMethods();
        dataBoxRow.add(new HalvedDataBox("Method Points",methods.size(),Beyond.bgColors.get(3),Beyond.iconFas.get(2)));
        mainBody.add(dataBoxRow);



        mainBody.add(new MainRow().add(realTimeWidgetFactory.create("Real-Time")));


        model.addAttribute("mainBody",mainBody.toHtml());
        return "index";
    }
}
