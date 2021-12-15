package com.pning.common.pojo;

/**
 * @Author Pning
 * @Date 2021/12/14 15:49
 * 用户注册时候需要填写的信息
 **/
public class UserMessage {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String fullname;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号状态：0暂时封号，1正常账号，2永久封号
     */
    private String status;
}
