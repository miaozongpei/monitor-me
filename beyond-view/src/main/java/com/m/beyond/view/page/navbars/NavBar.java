package com.m.beyond.view.page.navbars;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NavBar extends AbstractElement {
    private String sysName;

    public NavBar(String sysName) {
        this.sysName = sysName;
    }
}
