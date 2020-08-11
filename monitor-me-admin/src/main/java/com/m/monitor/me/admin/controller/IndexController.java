package com.m.monitor.me.admin.controller;

import com.m.beyond.view.page.databoxes.DataBox;
import com.m.beyond.view.vt.VTEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public  String index(Model model) {
        DataBox dataBox=new DataBox();
        dataBox.setText("你好");
        model.addAttribute("dataBox",dataBox.toHtml());
        return "index";
    }
}
