package com.m.monitor.me.admin.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageHtml {
    private String html;

    public PageHtml(String html) {
        this.html = html;
    }
}
