package com.m.beyond.view.page.mains;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class MainRow extends AbstractElement{
    private int colLg;
    private List<AbstractElement> elements=new ArrayList<>();
    private List<String> htmlElements=new ArrayList<>();

    public MainRow add(AbstractElement element){
        elements.add(element);
        htmlElements.add(element.toHtml());
        colLg=elements.size()==0?12:(12/elements.size());
        return this;
    }
}
