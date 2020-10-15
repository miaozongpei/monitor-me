package com.m.beyond.view.page.labels;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IconLabel extends Label {
    private String icon;
    private int size;
    public IconLabel(String icon, String bgColor, String text) {
        super(bgColor,text);
        this.icon=icon;
        this.size=15;
    }
}
