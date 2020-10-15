package com.m.beyond.view.page.forms.checkboxes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SliderCheckbox extends AbstactCheckBox {
    public SliderCheckbox(String name, String defaultValue, String text, boolean isChecked) {
        super(name, defaultValue, text, isChecked);
    }
}
