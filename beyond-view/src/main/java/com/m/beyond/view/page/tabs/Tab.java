package com.m.beyond.view.page.tabs;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Tab extends AbstractElement {
    private List<TabPane> tabPanes=new ArrayList<>();
    public Tab add(TabPane tabPane){
        if (tabPanes.size()==0){
            tabPane.setActive(true);
        }
        tabPanes.add(tabPane);
        return this;
    }
}
