package com.m.beyond.view.vt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.m.beyond.view.Beyond;
import com.m.beyond.view.data.ajaxs.AjaxData;
import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.charts.RealTimeLineChart;
import com.m.beyond.view.page.forms.ComboSelect;
import com.m.beyond.view.page.forms.SelectOption;
import com.m.beyond.view.page.mains.MainBody;
import com.m.beyond.view.page.mains.MainRow;
import com.m.beyond.view.page.tabs.Tab;
import com.m.beyond.view.page.tabs.TabPane;
import com.m.beyond.view.page.widgets.Widget;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class VTEngine {
    private static VelocityEngine ve = new VelocityEngine();
    private static String basePath = "page/";
    private static String basePackage="com.m.beyond.view.page.";
    private static String templateFileExt=".html";

    static {
        try {
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.setProperty("input.encoding", "UTF-8");
            ve.setProperty("output.encoding", "UTF-8");
            ve.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String parse(String template, Map<String, ?> model) {
        try {
            StringWriter result = new StringWriter();
            VelocityContext velocityContext = new VelocityContext(model);
            ve.getTemplate(basePath + template).merge(velocityContext, result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parse(AbstractElement element) {
        Map<String, Object> model= null;
        try {
            model = JSON.parseObject(JSON.toJSONString(element, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteMapNullValue), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String template=element.getClass().getName().replace(basePackage,"");
        template=template.replace(".","/")+templateFileExt;
        return parse(template,model);
    }

    public static void main(String[] args) {
        Map<String,String> datas=new HashMap<>();
        datas.put("sys.name","$('#sys.name').val()");//$('#sys.name').val()
        datas.put("point.method","$('#point.method').val()");
        datas.put("server.host","'1111.1.1.1'");
        AjaxData ajaxData=new AjaxData("/real_time/data",datas);
        RealTimeLineChart realTimeLineChart=new RealTimeLineChart(Beyond.colors.get(0),ajaxData);
System.out.println(realTimeLineChart.toHtml());



    }
}
