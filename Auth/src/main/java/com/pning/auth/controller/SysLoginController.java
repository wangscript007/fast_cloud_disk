package com.pning.auth.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Pning
 * @Date 2021/12/10 9:33
 **/
@RequestMapping("/auth")
@Controller
public class SysLoginController {

    @Secured(value = "{ROLE_vip1', 'ROLE_vip2'}")
    @GetMapping("test")
    public String test(){
        return "testSuccess";
    }

}
