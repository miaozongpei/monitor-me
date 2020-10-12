package com.m.beyond.view.page.divs;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchDiv extends AbstractElement {
    private MainBody body=new MainBody();
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
