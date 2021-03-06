package com.pning.admin.fastdfs.utlits;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author Pning
 * @Date 2021/12/30 15:30
 **/
public class UserUtil {
    /**
     * 获取当前登录用户的用户名
     * @return
     */
    public static String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
