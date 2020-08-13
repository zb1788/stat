package com.zzvcom.stat.base.exception;

import com.zzvcom.wrapper.WrapResponseMapper;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常捕获
 * @author boz
 * @date 2019/7/25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Object missingParameterException( MissingServletRequestParameterException Exception){
        return WrapResponseMapper.error("参数" + Exception.getParameterName() + "不能为空！");
    }

    @ExceptionHandler
    public Object nohandlerFoundException(NoHandlerFoundException Exception){
        return WrapResponseMapper.error("接口地址" + Exception.getRequestURL() + "不存在！");
    }


}
