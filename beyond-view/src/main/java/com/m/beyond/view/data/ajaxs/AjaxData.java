package com.m.beyond.view.data.ajaxs;

import lombok.Getter;

import java.util.Map;

@Getter
public class AjaxData {
    private String url;
    private Map<String,String> datas;

    public AjaxData(String url, Map<String, String> datas) {
        this.url = url;
        this.datas = datas;
    }
}
