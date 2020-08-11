package com.m.beyond.view.vt;

import com.m.beyond.view.page.databoxes.DataBox;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static String parse(Object obj) {
        Map<String, String> model= null;
        try {
            model = BeanUtils.describe(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String template=obj.getClass().getName().replace(basePackage,"");
        template=template.replace(".","/")+templateFileExt;
        return parse(template,model);
    }
    public static void main(String[] args) {
        DataBox dataBox=new DataBox();
        dataBox.setText("你好");
        System.out.println(dataBox.toHtml());


    }
}
