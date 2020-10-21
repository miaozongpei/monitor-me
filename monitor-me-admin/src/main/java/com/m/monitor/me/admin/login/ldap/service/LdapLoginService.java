package com.m.monitor.me.admin.login.ldap.service;

import com.m.monitor.me.admin.login.AbstractLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

@Service
public class LdapLoginService extends AbstractLoginService {
    @Autowired
    private LdapTemplate ldapTemplate;
    @Override
    public boolean loginAuth(String userName, String password) throws Exception {
        EqualsFilter filter = new EqualsFilter("cn", userName);
        return ldapTemplate.authenticate("", filter.toString(), password);
    }
}