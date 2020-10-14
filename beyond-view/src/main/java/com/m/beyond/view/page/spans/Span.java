package com.m.beyond.view.page.spans;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Span extends AbstractElement {
    private String text;

    public Span(String text) {
        this.text = text;
    }
}
