package com.kanlon.model;

import java.util.Map;
/**
 * 公共返回信息
 * @author zhangcanlong
 * @since 2019-04-12
 **/
public class CommonResponse {
    public Integer code;
    public String message;
    public Object resultData;
    public Map<String, String> messageData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public static CommonResponse failedResult() {
        return  failedResult("failed");
    }

    public static CommonResponse failedResult(String msg) {
        return  failedResult(msg, -1);
    }

    public static CommonResponse failedResult(String msg, int code) {
        return failedResult(msg, code, null);
    }

    public static CommonResponse failedResult(String msg, int code, Map<String, String> messageData) {
        return resultTemplate(null, msg, code, messageData);
    }

    public static CommonResponse succeedResult() {
        return succeedResult("succeed");
    }

    public static CommonResponse succeedResult(String msg) {
        return succeedResult(msg, 1);
    }

    public static CommonResponse succeedResult(String msg, int code) {
        return succeedResult(null, msg, code);
    }

    public static CommonResponse succeedResult(Object rst) {
        return succeedResult(rst, "succeed", 1);
    }

    public static CommonResponse succeedResult(Object rst, String msg) {
        return succeedResult(rst, msg, 1);
    }

    public static CommonResponse succeedResult(Object rst, String msg, int code) {
        return resultTemplate(rst, msg, code, null);
    }

    public static CommonResponse resultTemplate(Object rst, String msg, int code, Map<String, String> messageData) {
        CommonResponse res = new CommonResponse();
        res.setCode(code);
        res.setResultData(rst);
        res.setMessage(msg);
        res.setMessageData(messageData);
        return  res;
    }


    public Map<String, String> getMessageData() {
        return messageData;
    }

    public void setMessageData(Map<String, String> messageData) {
        this.messageData = messageData;
    }

    @Override
    public String toString() {
        return "CommonResponse{" + "code=" + code + ", message='" + message + '\'' + ", resultData=" + resultData + ", messageData=" + messageData + '}';
    }
}
