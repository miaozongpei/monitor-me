package com.m.monitor.me.admin.controller;

import com.m.beyond.view.data.forms.ResultMsg;
import com.m.monitor.me.service.mogodb.service.MonitorPointService;
import com.m.monitro.me.common.limit.PointLimit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/limit")
@Slf4j
public class LimitController {
    @Resource
    private MonitorPointService monitorPointService;

    @RequestMapping("/setting")
    @ResponseBody
    public ResultMsg setting(HttpServletRequest request, PointLimit pointLimit) {
        String token=(String)request.getSession().getAttribute(LoginController.LOGIN_TOKEN);
        String loginName=StringUtils.isNotEmpty(token)?token.split("_")[1]:null;
        if (StringUtils.isEmpty(loginName)||!"admin".equals(loginName)){
            return ResultMsg.fail("setting is no auth!");
        }
        String[] hosts=request.getParameterValues("hosts");
        String name=request.getParameter("sys_name");
        String method=request.getParameter("point_method");
        String type=request.getParameter("type");
        try {
            if ("delete".equals(type)) {
                monitorPointService.modifyMl(name, method, hosts, null);
                return ResultMsg.success("deleted successfully!");

            }else{
                monitorPointService.modifyMl(name, method, hosts, pointLimit);
                return ResultMsg.success("setting is successful!");
            }
        }catch (Exception e){
            log.error("setting pointLimit error:",e);
            return ResultMsg.fail("setting is fail!");
        }
    }
}
