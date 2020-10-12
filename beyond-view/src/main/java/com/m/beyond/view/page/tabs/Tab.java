package com.m.beyond.view.page.tabs;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class Tab extends AbstractElement {
    protected AjaxData ajax;
    protected List<TabPane> tabPanes=new ArrayList<>();
    public Tab(AjaxData ajax) {
        this.ajax = ajax;
    }
    public Tab add(TabPane tabPane){
        if (tabPanes.size()==0){
            tabPane.setActive(true);
        }
        tabPanes.add(tabPane);
        return this;
    }
}
