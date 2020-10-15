package com.m.beyond.view.page.forms.inputs;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractInput extends AbstractElement {
    protected String name;
    protected String defaultValue;
    protected String bindOnchangeFunction;

    public AbstractInput(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }
}
