package com.example.onlineeducationplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 功能：
 * 作者：ddh
 * 日期： 2024/9/19 14:31
 */
@Data
@TableName("user")
//@AllArgsConstructor  //构造器，构造函数全部属性
//@NoArgsConstructor  //无参构造器
//@Builder  //帮忙创建一个User对象
public class User {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    @TableField(exist = false)  //表示这个字段在我们数据库中并不存在
    private String token;
    private String role;
//    public static void main(String []args){
//        User user = User.builder().name("ddh").build();
//        System.out.println(user.getName());
//    }
}
//下一步改造mapper