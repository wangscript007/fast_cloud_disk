package com.pning.admin.test.controller;

import com.pning.admin.fastdfs.utlits.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * @Author Pning
 * @Date 2021/12/10 9:33
 * 用于测试权限拦截功能时候实现
 **/
@RequestMapping("/user")
@Controller
public class TestFilterController {

    @RolesAllowed(value = {"user:view"})
    @GetMapping("/list")
    public String test(){
        System.out.println("权限拦截成功"+ UserUtil.getUserName());
        return "testSuccess";
    }

}
