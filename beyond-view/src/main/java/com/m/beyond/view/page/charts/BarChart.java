package com.m.beyond.view.page.charts;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarChart extends AbstractElement {
    private AjaxData ajax;

    public BarChart(AjaxData ajax) {
        this.ajax = ajax;
    }
}
