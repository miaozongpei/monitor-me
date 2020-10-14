package com.m.beyond.view.data.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMsg {
    private String type;
    private String msg;
    private String icon;

    public ResultMsg(String type, String msg,String icon) {
        this.type = type;
        this.msg = msg;
        this.icon=icon;
    }

    public static ResultMsg success(String msg){
        return new ResultMsg("success",msg,"fa-check");
    }
    public static ResultMsg fail(String msg){
        return new ResultMsg("danger",msg,"fa-bolt");
    }

}
