package com.example.onlineeducationplatform.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：接口统一返回包装类
 * 作者：ddh
 * 日期： 2024/9/19 10:00
 */
@Data
@AllArgsConstructor   //自动创建所有属性构造方法
@NoArgsConstructor //自动创建无参构造方法
@Builder  //一个构建者模式，帮你去穿件对象
public class Result {

    public static final String CODE_SUCCESS="200";
    public static final String CODE_AUTH_ERROR="401";
    public static final String CODE_SYS_ERROR="500";

    private String code;  //请求返回编码  401-无权限  404-没有资源  405--接口的请求类型写错了  400--接口参数错误
    private  String msg;  //表示错误详细信息
    private Object data;   //数据从什么地方返回（承载数据）  user Object类型就是User   List Object类型就是List   map Object类型就是map

    public static Result success(){
//        return new Result(CODE_SUCCESS,"请求成功",null);
        return Result.builder().code(CODE_SUCCESS).msg("请求成功").build();  //直接通过builder创建对象
    }
    public static Result success(Object data){
        return new Result(CODE_SUCCESS,"请求成功",data);
    }
    public static Result error(String msg){
        return new Result(CODE_SYS_ERROR,msg,null);
    }
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
    public static Result error(){
        return new Result(CODE_SYS_ERROR,"系统错误",null);
    }
}
