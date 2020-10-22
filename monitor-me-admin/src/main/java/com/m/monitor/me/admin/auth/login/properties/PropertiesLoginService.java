package com.m.monitor.me.admin.auth.login.properties;

import com.m.monitor.me.admin.auth.login.AbstractLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesLoginService extends AbstractLoginService {
    @Value("${monitor.me.auth.login.name:admin}")
    private String loginAdminName="admin";
    @Value("${monitor.me.auth.login.password:admin}")
    private String loginAdminPwd="admin";
    @Override
    public boolean loginAuth(String userName, String password) {
        return loginAdminName.equals(userName)&&loginAdminPwd.equals(password);
    }
}