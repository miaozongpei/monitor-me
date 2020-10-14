package com.m.beyond.view.page.widgets;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.functions.AbstractFunction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormWidget extends Widget {
    private AbstractFunction formFunction;
    private String formFunctionHtml;
    private String formFunctionName;
    public FormWidget(String headTitle,AbstractFunction formFunction){
        super(headTitle);
        initFormFunction(formFunction);
    }
    private void initFormFunction(AbstractFunction formFunction){
        this.formFunction=formFunction;
        this.formFunctionHtml=formFunction.toHtml();
        this.formFunctionName=formFunction.getClass().getSimpleName();
    }
    public void setFormFunction(AbstractFunction formFunction) {
        initFormFunction(formFunction);
    }
}
