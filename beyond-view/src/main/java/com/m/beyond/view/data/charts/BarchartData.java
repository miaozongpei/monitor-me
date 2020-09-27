package com.m.beyond.view.data.charts;


import com.m.beyond.view.Beyond;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class BarchartData implements Serializable {

    private List<Map<String,Object>> data=new ArrayList<>();
    private String xkey;
    private String[] ykeys;
    private String[] labels;
    private String hideHover="auto";
    private String[] barColors;

    public void putData(String xKey,String xValue,Map<String,Double> yData){
        this.xkey=xKey;
        this.ykeys= yData.keySet().toArray(new String[0]);
        this.labels=ykeys;
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put(xKey,xValue);
        dataMap.putAll(yData);
        this.data.add(dataMap);

        initBarColors(yData.size());

    }

    private void initBarColors(int size){
        this.barColors=new String[size];
        for (int i=0;i<size;i++){
            this.barColors[i]= Beyond.COLORS.get(i);
        }
    }

}
