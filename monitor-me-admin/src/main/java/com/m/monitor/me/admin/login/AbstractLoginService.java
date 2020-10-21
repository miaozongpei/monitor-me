package com.m.monitor.me.admin.login;

public abstract class AbstractLoginService {
    public abstract boolean loginAuth(String userName, String password)throws Exception;
}
