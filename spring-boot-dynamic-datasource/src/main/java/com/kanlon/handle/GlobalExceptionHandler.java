package com.kanlon.handle;

import com.kanlon.response.CommonResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 全局异常处理器，捕获控制层的异常并做统一处理返回
 * @author zhangcanlong
 * @since 2019-09-01
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e 自定义异常封装
     * @return json封装
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResponse customExceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof MethodArgumentNotValidException || e instanceof BindException) {
            BindingResult bindingResult = e instanceof MethodArgumentNotValidException ?
                    ((MethodArgumentNotValidException) e).getBindingResult() : ((BindException) e).getBindingResult();

            HashMap<String, String> messagesData = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                messagesData.put(error.getField(), error.getDefaultMessage());
            }
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setCode(-1000);
            commonResponse.setMessage("field error");
            commonResponse.setMessageData(messagesData);
            return CommonResponse.failedResult(messagesData.toString(),-1000);
        }
        return CommonResponse.succeedResult();
    }
}
