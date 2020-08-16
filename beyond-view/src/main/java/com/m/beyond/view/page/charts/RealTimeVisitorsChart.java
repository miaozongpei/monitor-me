package com.m.beyond.view.page.charts;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeVisitorsChart extends AbstractElement {
    private String elementId;
    private String color="themeprimary";
    public RealTimeVisitorsChart(String elementId) {
        this.elementId = elementId;
    }

    public RealTimeVisitorsChart(String elementId, String color) {
        this.elementId = elementId;
        this.color = color;
    }
}
