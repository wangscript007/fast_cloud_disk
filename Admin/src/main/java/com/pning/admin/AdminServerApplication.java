package com.pning.admin;

import com.pning.common.properties.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 /**
 * @Author Pning
 * @Date 2021/14/10 9:13
 **/

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class,args);
    }
}
