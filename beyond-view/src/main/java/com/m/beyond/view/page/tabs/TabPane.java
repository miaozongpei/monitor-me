package com.m.beyond.view.page.tabs;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
public class TabPane extends AbstractElement {
    private String title;
    private String type;
    private boolean isActive=false;
    private AbstractElement body;
    private String htmlBody;

    public TabPane(String title,String type,AbstractElement body) {
        super();
        this.title = title;
        this.type=type;
        this.setBody(body);
    }

    public void setBody(AbstractElement body) {
        this.body = body;
        if (body!=null) {
            this.htmlBody = body.toHtml();
        }
    }
}
