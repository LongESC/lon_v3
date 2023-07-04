package com.lon.lon_v3.comomon;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());
//     全局异常拦截
    @ExceptionHandler
    public Result handlerException(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result handlerException(ConstraintViolationException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        return Result.error(e.getMessage());
    }

}

