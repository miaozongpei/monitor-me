package com.m.monitor.me.client.point.limit;

import lombok.Getter;
import lombok.Setter;

/**
 * 流控异常类
 * @Author: miaozp
 * @Date: 2020/10/31 5:56 下午
 **/
@Getter
@Setter
public class MonitorLimitException extends  Exception{
    private String code;
    private String message;


    public MonitorLimitException(String code,String message) {
        super(message);
        this.code=code;
        this.message=message;
    }

    public static enum ErrorEnum{
        /**
         * 监控点阻断异常
         **/
        POINT_BROKEN(new MonitorLimitException(
                "LIMIT:99999","The point is broken"
        )),
        /**
         * 监控点超出最大等待线程数异常
         **/
        POINT_OVER_WAITING_THREADS(new MonitorLimitException(
                "LIMIT:99998","The point of waiting threads over limit:%s"
        )),
        /**
         * 监控点超过最大TPS异常
         **/
        POINT_OVER_MAX_TPS(new MonitorLimitException(
                "LIMIT:99997","The point of TPS over max:%s"
        ))

        ;
        public MonitorLimitException exception;
        private ErrorEnum(MonitorLimitException exception) {
            this.exception = exception;
        }
        public MonitorLimitException format(Object ... args){
            this.exception.setMessage(String.format(this.exception.message,args));
            return this.exception;
        }
    }
}
