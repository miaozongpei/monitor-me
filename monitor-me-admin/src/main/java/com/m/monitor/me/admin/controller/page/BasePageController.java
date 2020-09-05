package com.m.monitor.me.admin.controller.page;
import com.m.monitor.me.admin.page.PageHtml;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


public abstract class BasePageController {

    public abstract PageHtml html(HttpServletRequest request, @PathVariable("type") String type);
}
