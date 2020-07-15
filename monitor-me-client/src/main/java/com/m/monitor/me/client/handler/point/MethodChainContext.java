package com.m.monitor.me.client.handler.point;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法链
 */
public class MethodChainContext {
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
