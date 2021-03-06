package com.m.monitor.me.client.point.collector;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法调用链
 *
 * @Author: miaozp
 * @Date: 2020/10/31 5:37 下午
 **/
@Getter
public class MethodChains {
    private MethodChain methodChain;
    private Integer chainLevel = 0;
    private Long time = 0L;
    private Long totalTime = 0L;
    private Long childrenTotalTime = 0L;
    private Long selfTime = 0L;
    private List<MethodChains> children;

    public MethodChains(MethodChain methodChain, int chainLevel, long time, long totalTime) {
        this.methodChain = methodChain;
        this.time = time;
        this.totalTime = totalTime;
        this.chainLevel = chainLevel;
    }

    /**
     * 构建调用链的层级关系
     *
     * @param point:
     * @Author: miaozp
     * @Date: 2020/10/31 5:38 下午
     * @return: void
     **/
    public void buildChildren(MonitorPoint point) {
        for (MethodChain methodChain : point.getChains()) {
            if (methodChain != null && methodChain.getParentIndex() != null && methodChain.getParentIndex() == this.methodChain.getInvokeSeq()) {
                Long time = point.get(methodChain.getInvokeSeq());
                if (time != null) {
                    MethodChains child = new MethodChains(methodChain, this.chainLevel + 1, time, point.getNorm());
                    child.buildChildren(point);
                    this.addChild(child);
                }
            }
        }
    }

    public void addChild(MethodChains chain) {
        if (children == null) {
            this.children = new ArrayList<MethodChains>();
        }
        childrenTotalTime += chain.getTime();
        this.children.add(chain);
    }

    public Long getSelfTime() {
        this.selfTime = this.time - childrenTotalTime;
        return this.selfTime;
    }

    public String childrenToStr(List<MethodChains> children) {
        if (CollectionUtils.isEmpty(children)) {
            return "";
        }
        StringBuffer str = new StringBuffer();
        for (MethodChains child : children) {
            for (int i = 0; i < child.chainLevel; i++) {
                str.append("  ");
            }
            str.append(toStrMethodChains(child));
            str.append("\n");

            str.append(childrenToStr(child.getChildren()));
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return toStrMethodChains(this);
    }

    /**
     * 格式化输出调用链
     *
     * @param methodChains:
     * @Author: miaozp
     * @Date: 2020/10/31 5:38 下午
     * @return: java.lang.String
     **/
    private String toStrMethodChains(MethodChains methodChains) {
        methodChains.getSelfTime();
        StringBuffer str = new StringBuffer();
        str.append("[");
        str.append(new DecimalFormat("0.00%").format(methodChains.selfTime.doubleValue() / methodChains.getTotalTime())).append(",");
        str.append(methodChains.selfTime).append("ms");
        str.append("]-");
        str.append(methodChains.methodChain.getMethodName());
        return str.toString();
    }

}
