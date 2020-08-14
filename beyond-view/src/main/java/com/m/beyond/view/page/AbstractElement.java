package com.m.beyond.view.page;

import com.m.beyond.view.vt.VTEngine;

public abstract class AbstractElement {
    public String toHtml(){
        return VTEngine.parse(this);
    }
}
