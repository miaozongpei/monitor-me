package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Widget extends AbstractElement {
    private String headTitle;
    private String widgetIcon;
    private boolean isHeadBottom=false;//

    private boolean isMaximize=false;//最大化
    private boolean isCollapse=false;//折叠
    private boolean isDispose=false;//关闭
    private AbstractElement body;
    public Widget(){

    }
    public Widget(String headTitle, AbstractElement body) {
        this.headTitle = headTitle;
        setBody(body);
    }

    private String htmlBody;

    public void setBody(AbstractElement body) {
        this.body = body;
        if (body!=null) {
            this.htmlBody = body.toHtml();
        }
    }
}
