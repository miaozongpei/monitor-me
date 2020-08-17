package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ComboSelect extends AbstractElement {
    private String name;
    private List<SelectOption> options=new ArrayList<>();

    public ComboSelect(String name) {
        this.name = name;
    }
    public ComboSelect add(SelectOption option){
        options.add(option);
        return this;
    }
}
