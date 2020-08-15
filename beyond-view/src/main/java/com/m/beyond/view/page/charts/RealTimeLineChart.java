package com.m.beyond.view.page.charts;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RealTimeLineChart extends AbstractElement {
    private String elementId;
    private String color="themeprimary";

    public RealTimeLineChart(String elementId) {
        this.elementId = elementId;
    }

    public RealTimeLineChart(String elementId, String color) {
        this.elementId = elementId;
        this.color = color;
    }
}
