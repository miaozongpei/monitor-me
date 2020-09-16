package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Select2 extends AbstractElement {
    private String name;
    private List<SelectOption> options=new ArrayList<>();
    private String bindOnchangeFunction;
    public Select2(String name) {
        this.name = name;
    }
    public Select2 add(SelectOption option){
        options.add(option);
        return this;
    }
}
