package com.m.beyond.view.page.charts;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RealTimeLineChart extends AbstractElement {
    private String elementId;

    public RealTimeLineChart(String elementId) {
        this.elementId = elementId;
    }
}
