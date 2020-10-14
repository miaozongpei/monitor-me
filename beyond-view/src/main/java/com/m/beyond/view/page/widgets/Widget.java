package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Widget extends AbstractElement {
    private String headTitle;
    private String headColor;
    private String widgetIcon;
    private boolean isWarning=false;//是否告警
    private boolean isHeadBottom=false;//

    private boolean isMaximize=false;//最大化
    private boolean isCollapse=false;//折叠
    private boolean isDispose=false;//关闭
    private MainBody body=new MainBody();
    public Widget(){
    }

    public Widget(String headTitle) {
        this.headTitle = headTitle;
    }

    public Widget(String headTitle, MainBody body) {
        this.headTitle = headTitle;
        setBody(body);
    }
    public Widget(String headTitle, AbstractElement body) {
        this.headTitle = headTitle;
        setBody(new MainBody().add(new MainRow().add(body)));
    }

    private String htmlBody;

    public void setBody(MainBody body) {
        this.body = body;
        if (body!=null) {
            this.htmlBody = body.toHtml();
        }
    }
    public void addRow(MainRow row){
        this.body.add(row);
        this.htmlBody = body.toHtml();
    }
}
