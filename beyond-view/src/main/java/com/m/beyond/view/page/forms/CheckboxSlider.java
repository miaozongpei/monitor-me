package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckboxSlider extends AbstractElement {
    private String text;

    public CheckboxSlider(String text) {
        this.text = text;
    }
}
