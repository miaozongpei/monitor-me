package com.m.monitor.me.admin.page;

import javax.servlet.http.HttpServletRequest;

public abstract class BasePage {
    public final static String POINTS="points";
    public final static String TIME_TAB="time_tab";
    public final static String LIMIT="limit";
    public final static String METHOD_LIMIT_SETTING="method_limit_setting";

    public abstract PageHtml create(HttpServletRequest request);
}
