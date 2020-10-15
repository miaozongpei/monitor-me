package com.m.beyond.view.page.forms.checkboxes;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstactCheckBox extends AbstractElement {
    protected String name;
    protected String defaultValue;
    protected String text;
    protected boolean isChecked=true;
    protected String bindOnchangeFunction;


    public AbstactCheckBox(String name,String defaultValue, String text,boolean isChecked) {
        this.name = name;
        this.defaultValue=defaultValue;
        this.text = text;
        this.isChecked=isChecked;
    }
}
