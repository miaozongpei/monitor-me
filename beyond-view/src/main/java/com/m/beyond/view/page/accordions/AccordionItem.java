package com.m.beyond.view.page.accordions;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccordionItem extends AbstractElement {
    private String title;
    private String bodyTxt;

    public AccordionItem(String title, String bodyTxt) {
        this.title = title;
        this.bodyTxt = bodyTxt;
    }
}
