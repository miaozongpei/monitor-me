package com.m.monitor.me.client.handler.point;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法链
 */
public class MethodChainContext {
        public static Map<String,ArrayList<String>> chainMap=new ConcurrentHashMap<>();
        public static void put(String rootMethodName,String fullMethodName){
                chainMap.get(rootMethodName).add(fullMethodName);
        }
}
