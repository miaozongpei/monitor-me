package com.m.beyond.view.page.menus;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SidebarMenu extends AbstractElement {
    private List<MenuItem> items=new ArrayList();
    public void add(MenuItem item){
        items.add(item);
    }
}
