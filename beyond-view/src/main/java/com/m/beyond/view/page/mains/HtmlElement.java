package com.m.beyond.view.page.mains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HtmlElement {
    private String html;
    private int colLg;

    public HtmlElement(String html, int colLg) {
        this.html = html;
        this.colLg = colLg;
    }
}
