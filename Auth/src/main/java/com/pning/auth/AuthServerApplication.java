package com.pning.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.pning.common.properties.RsaKeyProperties;

/**
 /**
 * @Author Pning
 * @Date 2021/12/10 9:13
 * 记得把写好的RsaKeyProperties放入ioc容器中
 **/

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
