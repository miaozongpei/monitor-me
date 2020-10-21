package com.m.monitor.me.admin.login.properties;

import com.m.monitor.me.admin.login.AbstractLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

@Service
public class PropertiesLoginService extends AbstractLoginService {

    private String loginAdminName="admin";
    private String loginAdminPwd="admin";
    @Override
    public boolean loginAuth(String userName, String password) {
        return loginAdminName.equals(userName)&&loginAdminPwd.equals(password);
    }
}