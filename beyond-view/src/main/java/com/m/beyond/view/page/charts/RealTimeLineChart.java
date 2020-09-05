package com.m.beyond.view.page.charts;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RealTimeLineChart extends AbstractElement {
    private String color;
    private AjaxData ajax;
    public RealTimeLineChart(String color,AjaxData ajax) {
        super();
        this.color = color;
        this.ajax=ajax;
    }
}
