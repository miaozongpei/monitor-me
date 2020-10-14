package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormWidget extends Widget {
    public FormWidget(String headTitle){
        super(headTitle);
    }
    public FormWidget(String headTitle, AbstractElement body){
        super(headTitle,body);
    }
}
