package com.m.beyond.view.page.divs;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormGroupDiv extends AbstractElement {
    private String label;
    private AbstractElement input;
    private String inputHtml;

    public FormGroupDiv(String label, AbstractElement input) {
        this.label = label;
        this.input = input;
        this.inputHtml=input.toHtml();
    }
}
