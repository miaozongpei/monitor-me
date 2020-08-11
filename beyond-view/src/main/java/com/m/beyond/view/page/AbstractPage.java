package com.m.beyond.view.page;

import com.m.beyond.view.vt.VTEngine;

public abstract class AbstractPage {
    public String toHtml(){
        return VTEngine.parse(this);
    }
}
