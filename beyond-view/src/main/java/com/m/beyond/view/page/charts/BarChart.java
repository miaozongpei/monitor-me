package com.m.beyond.view.page.charts;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BarChart extends AbstractElement {
    private String id;
    public BarChart(String id){
        this.id=id;
    }
}
