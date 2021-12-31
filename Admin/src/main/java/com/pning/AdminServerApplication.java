package com.pning;

import com.pning.common.properties.RsaKeyProperties;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

 /**
 * @Author Pning
 * @Date 2021/14/10 9:13
 **/
@EnableSwagger2Doc
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class,args);
    }
}
