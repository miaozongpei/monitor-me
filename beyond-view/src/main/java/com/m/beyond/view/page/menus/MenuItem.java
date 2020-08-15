package com.m.beyond.view.page.menus;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MenuItem extends AbstractElement {
    private String actionUrl="#";
    private String menuIcon="fa-align-justify";
    private boolean isActive=false;

    public MenuItem(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public MenuItem(String actionUrl, String menuIcon) {
        this.actionUrl = actionUrl;
        this.menuIcon = menuIcon;
    }

    public MenuItem(String actionUrl, boolean isActive) {
        this.actionUrl = actionUrl;
        this.isActive = isActive;
    }

    public MenuItem(String actionUrl, String menuIcon, boolean isActive) {
        this.actionUrl = actionUrl;
        this.menuIcon = menuIcon;
        this.isActive = isActive;
    }

    private List<MenuItem> subItems=new ArrayList<>();

    private MenuItem add(MenuItem subItem){
        subItems.add(subItem);
        return this;
    }
}
