package com.m.monitor.me.admin.controller.page;

import com.m.monitor.me.admin.page.BasePage;
import com.m.monitor.me.admin.page.PageHtml;
import com.m.monitor.me.admin.page.points.LimitPage;
import com.m.monitor.me.admin.page.points.MethodLimitSettingPage;
import com.m.monitor.me.admin.page.points.PointsPage;
import com.m.monitor.me.admin.page.points.TimeTabPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/p")
public class PageController extends BasePageController {
    @Resource
    private PointsPage pointsPage;
    @Resource
    private TimeTabPage timeTabPage;
    @Resource
    private LimitPage limitPage;
    @Resource
    private MethodLimitSettingPage methodLimitSettingPage;
    @RequestMapping("/{type}")
    @ResponseBody
    @Override
    public PageHtml html(HttpServletRequest request, @PathVariable("type") String type) {
        PageHtml pageHtml=null;
        if (BasePage.POINTS.equals(type)){
            pageHtml=pointsPage.create(request);
        }else if (BasePage.TIME_TAB.equals(type)){
            pageHtml=timeTabPage.create(request);
        }else if (BasePage.LIMIT.equals(type)){
            pageHtml= limitPage.create(request);
        }else if (BasePage.METHOD_LIMIT_SETTING.equals(type)){
            pageHtml= methodLimitSettingPage.create(request);
        }
        return pageHtml;
    }
}
