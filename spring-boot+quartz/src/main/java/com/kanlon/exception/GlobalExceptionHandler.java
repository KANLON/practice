package com.kanlon.exception;


import com.kanlon.model.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


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
	public CommonResponse defaultErrorHandler( Exception e) {
		logger.error("内部服务器错误！", e);
		return CommonResponse.failedResult(e.getMessage(),-10001);
	}
}
