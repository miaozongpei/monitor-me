package com.m.beyond.view.page.labels;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Label extends AbstractElement {
    private String bgColor;
    private String text;

    public Label(String bgColor, String text) {
        this.bgColor = bgColor;
        this.text = text;
    }
}
