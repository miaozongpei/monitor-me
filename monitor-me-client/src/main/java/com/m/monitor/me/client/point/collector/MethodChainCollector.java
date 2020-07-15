package com.m.monitor.me.client.point.collector;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法链
 */
public class MethodChainCollector {
        public static final String STOP="0";
        public static Map<String, ArrayList<String>> chainMap=new ConcurrentHashMap<>();
        public static  void checkAndPut(String rootMethodName,String chainName){
                ArrayList<String> root=chainMap.get(rootMethodName);
                int size=root.size();
                if (size>0&&STOP.equals(root.get(size-1))){
                        return;
                }
                root.add(chainName);
        }
}
