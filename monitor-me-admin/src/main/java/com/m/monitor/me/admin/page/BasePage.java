package com.m.monitor.me.admin.page;

import javax.servlet.http.HttpServletRequest;

public abstract class BasePage {
    public final static String points="points";
    public final static String time_tab="time_tab";
    public abstract PageHtml create(HttpServletRequest request);
}
