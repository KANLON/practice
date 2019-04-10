package com.kanlon.model;

/**
 * 返回数据的
 *
 * @author zhangcanlong
 * @since 2019/4/9 21:32
 **/
public class ReturnMsg {


    private String code;
    private String message;
    private Object ret;
    public ReturnMsg(){

    }
    public ReturnMsg(String code,String message){
        this.code = code;
        this.message = message;
    }


    public ReturnMsg(String code,String message,String ret){
        this.code = code;
        this.message = message;
        this.ret =ret;
    }

    @Override
    public String toString() {
        return "ReturnMsg{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", ret=" + ret + '}';
    }
}
