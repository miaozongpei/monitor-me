package com.m.beyond.view.page.accordions;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckBoxAccordion extends Accordion {
    private String name;

    public CheckBoxAccordion(String name) {
        this.name = name;
    }
}
