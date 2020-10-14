package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.spans.Span;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckBoxWidget extends Widget {
    public CheckBoxWidget(String headTitle, AbstractElement body){
        super(headTitle,body);
    }
}
