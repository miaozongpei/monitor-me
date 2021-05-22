package com.m.monitor.me.admin.controller;
import com.m.beyond.view.data.forms.ResultMsg;
import com.m.monitor.me.admin.auth.login.face.FaceLoginService;
import com.m.monitor.me.admin.auth.login.ldap.LdapLoginService;
import com.m.monitor.me.admin.auth.login.properties.PropertiesLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@Slf4j
public class LoginController {
    public static final String PAGE_LOGIN="login";
    public static final String PAGE_LOGOUT="logout";

    public static final String PAGE_INDEX="redirect:/index";
    public static final String LOGIN_TOKEN="LOGIN_TOKEN";

    @Resource
    private LdapLoginService ldapLoginService;

    @Resource
    private PropertiesLoginService propertiesLoginService;

    @RequestMapping(PAGE_LOGIN)
    public String login(Model model) {
        return PAGE_LOGIN;
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResultMsg doLogin(HttpServletRequest request) {
        String loginName=request.getParameter("loginName");
        String loginPwd=request.getParameter("loginPwd");
        String ldapFlag=request.getParameter("ldapFlag");
        boolean isAuth= false;
        try {
            isAuth = "1".equals(ldapFlag)?ldapLoginService.loginAuth(loginName,loginPwd):propertiesLoginService.loginAuth(loginName,loginPwd);
            log.info("loginName:{},isAuth:{}",loginName,isAuth);
        } catch (Exception e) {
            log.error("doLogin error:",e);
            return ResultMsg.fail(e.getMessage());
        }
        if (isAuth){
            request.getSession().setAttribute(LOGIN_TOKEN,UUID.randomUUID().toString()+"_"+loginName);
            return ResultMsg.success("Login success!");
        }
        return ResultMsg.fail("Name or Password is incorrect!");
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_TOKEN);
        return PAGE_INDEX;
    }
    @Resource
    private FaceLoginService faceLoginService;
    @RequestMapping("/doFaceLogin")
    @ResponseBody
    public ResultMsg doFaceLogin(HttpServletRequest request,String image) {
        boolean isAuth=false;
        try {
             isAuth=faceLoginService.loginAuth(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isAuth){
            request.getSession().setAttribute(LOGIN_TOKEN,UUID.randomUUID().toString());
            return ResultMsg.success("Login success!");
        }
        return ResultMsg.fail("Name or Password is incorrect!");
    }
}
