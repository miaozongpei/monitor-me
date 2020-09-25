package com.m.monitor.me.client.point.collector;

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
                int size=root.size();
                if (size>0) {
                        MethodChain lastChain = root.get(size - 1);
                        if (lastChain==null){
                                return;
                        }
                }
                root.add(methodName);
        }
}
