package com.m.beyond.view.page.functions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormAjaxConfirmForResultMsg extends AbstractFunction {
    private String formId;
    private String url;
    private String functionName;
    public FormAjaxConfirmForResultMsg(String url) {
        super();
        this.formId="form-"+this.id;
        this.url = url;
        this.functionName=this.getClass().getSimpleName();
    }

}
