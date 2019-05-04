package com.kanlon.exception;


import com.kanlon.model.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


/**
 * 全局异常处理类
 *
 * @author zhangcanlong
 * @date 2019-04-12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse defaultErrorHandler(Exception e) {
        logger.error("内部服务器错误！", e);
        e.printStackTrace();
        //处理验证错误，MethodArgumentNotValidException
        if (e instanceof MethodArgumentNotValidException) {
            HashMap<String, String> messagesData = new HashMap<>(16);
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                messagesData.put(error.getField(), error.getDefaultMessage());
            }
            return CommonResponse.failedResult(messagesData.toString());
        }
        return CommonResponse.failedResult(e.getLocalizedMessage());
    }
}
