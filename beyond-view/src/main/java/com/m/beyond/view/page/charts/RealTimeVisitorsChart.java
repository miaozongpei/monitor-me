package com.m.beyond.view.page.charts;

import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeVisitorsChart extends AbstractElement {
    private String color;
    private AjaxData ajax;
    private String visitorsChartRangesMinId;
    private String visitorsChartRangesMaxId;
    public RealTimeVisitorsChart(String color,AjaxData ajax) {
        super();
        this.color = color;
        this.ajax=ajax;
        this.visitorsChartRangesMinId=super.id;
        this.visitorsChartRangesMaxId=super.id;
    }
}
