package com.m.beyond.view.page.accordions;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Accordion extends AbstractElement {
    private List<AccordionItem> items=new ArrayList<>();
    public Accordion add(AccordionItem item){
        items.add(item);
        return this;
    }
}
