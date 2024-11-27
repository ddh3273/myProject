package com.example.onlineeducationplatform.common;

import lombok.Data;

import java.util.List;

/**
 * 功能：
 * 作者：ddh
 * 日期： 2024/9/20 10:34
 */
@Data
public class Page<T>{
    private Integer total;
    private List<T> list;
}
