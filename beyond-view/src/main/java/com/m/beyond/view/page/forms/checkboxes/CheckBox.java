package com.m.beyond.view.page.forms.checkboxes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckBox extends AbstactCheckBox {

    public CheckBox(String name, String defaultValue, String text,Boolean isChecked) {
        super(name, defaultValue, text,isChecked);
    }
}
