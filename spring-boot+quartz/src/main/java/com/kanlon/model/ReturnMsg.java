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

    public static ReturnMsg successReturn(String code,Object ret){
        ReturnMsg returnMsg = new ReturnMsg();
        returnMsg.code = code;
        returnMsg.ret=ret;
        return returnMsg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "ReturnMsg{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", ret=" + ret + '}';
    }



}
