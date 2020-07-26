package com.m.monitor.me.client.transfer.file;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;

import java.io.*;

public class MonitorWriterManager {
    private static String monitorBasePath = "~";
    private static BufferedWriter objWriter = null;
    private static MonitorWriterManager monitorWriterManager=new MonitorWriterManager();
    private MonitorWriterManager() {
        create();
    }
    public static MonitorWriterManager getInstance(){
        return monitorWriterManager;
    }
    private static void create(){
        try {
            String fileName="integrator.0";
            objWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(monitorBasePath+fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public  boolean writer(String integrator){
        try {
            objWriter.write(integrator);
            objWriter.newLine();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public  void flush(){
        try {
            objWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void close(){
        if (objWriter != null) {
            try {
                objWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}


