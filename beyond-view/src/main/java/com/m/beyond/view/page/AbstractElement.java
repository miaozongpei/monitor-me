package com.m.beyond.view.page;

import com.m.beyond.view.vt.VTEngine;

public abstract class AbstractElement {
    private int colLg;
    public String toHtml(){
        return VTEngine.parse(this);
    }

    public int getColLg() {
        return colLg;
    }

    public AbstractElement setLg(int colLg) {
        this.colLg = colLg;
        return this;
    }
}
