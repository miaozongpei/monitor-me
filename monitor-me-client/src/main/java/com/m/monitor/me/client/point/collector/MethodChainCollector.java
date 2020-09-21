package com.m.monitor.me.client.point.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法链
 */
public class MethodChainCollector {
        public static Map<String, List<MethodChain>> chainMap=new ConcurrentHashMap<>();
        public static  void checkAndPut(String rootMethodName,MethodChain methodName){
                List<MethodChain> root=chainMap.get(rootMethodName);
                root.add(methodName);
        }
}
