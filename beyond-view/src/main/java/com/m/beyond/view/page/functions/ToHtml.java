package com.m.beyond.view.page.functions;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ToHtml extends AbstractFunction {
    private AjaxData ajax;
    private String toHtmlId;
    public ToHtml(AjaxData ajaxData, String toHtmlId) {
        this.ajax = ajaxData;
        this.toHtmlId = toHtmlId;
    }
}
