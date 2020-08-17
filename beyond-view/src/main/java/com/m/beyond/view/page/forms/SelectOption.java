package com.m.beyond.view.page.forms;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SelectOption {
    private String label;
    private String value;

    public SelectOption( String value,String label) {
        this.label = label;
        this.value = value;
    }
}
