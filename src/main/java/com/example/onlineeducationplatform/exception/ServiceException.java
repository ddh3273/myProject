package com.example.onlineeducationplatform.exception;

import lombok.Data;

/**
 * 功能：自定义的异常类
 * 作者：ddh
 * 日期： 2024/9/22 9:39
 */
@Data
public class ServiceException extends RuntimeException{

    private String code;
   public ServiceException(String msg){
       super(msg);
       this.code="500";
   }
   public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }

}
