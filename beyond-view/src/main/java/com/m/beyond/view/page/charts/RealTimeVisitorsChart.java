package com.m.beyond.view.page.charts;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeVisitorsChart extends AbstractElement {
    private String color="themeprimary";
    public RealTimeVisitorsChart(String color) {
        super();
        this.color = color;
    }
}
