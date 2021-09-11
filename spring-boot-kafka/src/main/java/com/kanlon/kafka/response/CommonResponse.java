package com.kanlon.kafka.response;

import lombok.Data;

import java.util.Map;

/**
 * 公共的返回类
 *
 * @author zhangcanlong
 * @since 2019-10-31 修改，添加描述
 **/
@Data
public class CommonResponse<T> {

    /**
     * 1表示成功，非1表示失败
     **/
    private Integer code;

    /**
     * 成功或失败的信息，成功返回"succeed"，一般失败返回"failed" ,其他失败根据具体业务场景返回信息
     **/
    private String message;

    /**
     * 返回的结果数据
     **/
    private T resultData;

    /**
     * 信息相关的数据，一般都是失败信息相关的数据
     **/
    private Map<String, String> messageData;

    /**
     * 构造方法
     **/
    private CommonResponse() {}

    /**
     * 包含参数的构造方法
     **/
    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;

    }

    /**
     * 包含参数的构造方法
     **/
    private CommonResponse(int code, String message, T t, Map<String, String> messageData) {
        this.code = code;
        this.message = message;
        this.resultData = t;
        this.messageData = messageData;
    }


    /**
     * 泛型的公共的失败响应信息
     *
     * @param message 信息
     * @return 包含失败信息的公共响应类
     **/
    public static <T> CommonResponse<T> failedResultWithGenerics(String message) {
        return new CommonResponse<>(-1, message, null, null);
    }

    /**
     * 泛型的公共的失败响应信息
     *
     * @param message 错误信息
     * @param code    错误代码
     * @return 包含失败信息的公共响应类
     **/
    public static <T> CommonResponse<T> failedResultWithGenerics(String message, int code) {
        return new CommonResponse<>(code, message, null, null);
    }

    /**
     * 泛型构造的返回成功的数据
     *
     * @param t 传递的数据的泛型
     * @return 含有数据的公共响应信息
     **/
    public static <T> CommonResponse<T> succeedResultWithGenerics(T t) {
        return new CommonResponse<>(1, "success", t, null);
    }


}
