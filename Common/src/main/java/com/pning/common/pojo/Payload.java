package com.pning.common.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/
/* 为了方便后期获取token中的用户信息，将token中载荷部分单独封装成一个对象
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
