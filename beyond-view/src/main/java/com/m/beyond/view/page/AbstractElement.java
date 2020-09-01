package com.m.beyond.view.page;

import com.m.beyond.view.vt.VTEngine;

import java.util.UUID;

public abstract class AbstractElement {
    protected String id;
    private int colLg;
    public AbstractElement(){
        this.id=UUID.randomUUID().toString().replace("-","");
    }
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

    public String getId() {
        return id;
    }
}
