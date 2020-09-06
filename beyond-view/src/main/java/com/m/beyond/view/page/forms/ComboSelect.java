package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.functions.AbstractFunction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ComboSelect extends AbstractElement {
    private String name;
    private List<SelectOption> options=new ArrayList<>();
    private String bindOnchangeFunction;
    public ComboSelect(String name) {
        this.name = name;
    }
    public ComboSelect add(SelectOption option){
        options.add(option);
        return this;
    }
}
