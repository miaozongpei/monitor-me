package com.m.beyond.view.page.forms.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckboxSliderInput extends AbstractInput {
    private String text;
    private boolean isChecked;

    public CheckboxSliderInput(String name, String defaultValue) {
        super(name, defaultValue);
    }

    public CheckboxSliderInput(String name, String defaultValue,String text,boolean isChecked) {
        super(name, defaultValue);
        this.text=text;
        this.isChecked=isChecked;
    }

}
