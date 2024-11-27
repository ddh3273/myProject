package com.example.onlineeducationplatform.exception;

import com.example.onlineeducationplatform.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 功能：
 * 作者：ddh
 * 日期： 2024/9/22 9:37
 */

@ControllerAdvice()
public class GlobalException {

    @ExceptionHandler(ServiceException.class)//捕获异常
//    @RequestBody //把result这样一个Java对象转成一个json
    public Result serviceException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)//捕获异常
//    @RequestBody //把result这样一个Java对象转成一个json
    public Result globalException(ServiceException e){
        e.printStackTrace();
        return Result.error("500","系统错误");
    }
}
