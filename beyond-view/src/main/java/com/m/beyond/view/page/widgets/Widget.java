package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Widget extends AbstractElement {
    private String headTitle;
    private boolean isMaximize=false;//最大化
    private boolean isCollapse=false;//折叠
    private boolean isDispose=false;//关闭

    private String body;

}
