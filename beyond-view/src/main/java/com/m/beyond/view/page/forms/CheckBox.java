package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckBox extends AbstractElement {
    private String name;
    private String text;
    private boolean isChecked=true;

    public CheckBox(String name, String text) {
        this.name = name;
        this.text = text;
    }
}
