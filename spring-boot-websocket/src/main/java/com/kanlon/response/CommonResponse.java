package com.kanlon.response;

import java.util.Map;

/**
 * 公共的返回类
 *
 * @author zhangcanlong
 * @since 2019-06-02
 **/
public class CommonResponse {
    /**
     * 状态码，1表示成功，其它表示失败
     */
    private Integer code;

    /**
     * 成功或者失败的消息
     */
    private String message;

    private Object resultData;
    private Map<String, String> messageData;

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
        return failedResult("failed");
    }

    public static CommonResponse failedResult(String msg) {
        return failedResult(msg, -1);
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
        return res;
    }


    public Map<String, String> getMessageData() {
        return messageData;
    }

    public void setMessageData(Map<String, String> messageData) {
        this.messageData = messageData;
    }
}
