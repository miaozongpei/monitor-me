package com.m.monitor.me.admin.page;

import javax.servlet.http.HttpServletRequest;

public abstract class BasePage {
    public final static String POINTS="points";
    public final static String TIME_TAB="time_tab";
    public abstract PageHtml create(HttpServletRequest request);
}
