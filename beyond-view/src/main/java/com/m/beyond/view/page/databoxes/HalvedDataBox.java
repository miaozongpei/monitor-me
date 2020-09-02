package com.m.beyond.view.page.databoxes;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HalvedDataBox extends AbstractElement {
    private String text;
    private int num;
    private String topColor;
    private String topIcon;

    public HalvedDataBox(String text, int num, String topColor, String topIcon) {
        this.text = text;
        this.num = num;
        this.topColor = topColor;
        this.topIcon = topIcon;
    }
}
