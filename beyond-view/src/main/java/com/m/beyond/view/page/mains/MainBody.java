package com.m.beyond.view.page.mains;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class MainBody extends AbstractElement {
    private List<MainRow> rows=new ArrayList<>();
    private List<String> htmlRows=new ArrayList<>();
    public MainBody add(MainRow row){
        rows.add(row);
        htmlRows.add(row.toHtml());
        return this;
    }
}
