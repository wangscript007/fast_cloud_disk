package com.pning.admin.auth.config;


import com.pning.admin.auth.filter.JwtVerifyFilter;
import com.pning.common.properties.RsaKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.pning.admin.auth.filter.JwtLoginFilter;
import com.pning.admin.auth.service.ISystemUser;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 * 向SpringSecurity中添加我们自己写的拦截器：
 *      1:JwtLoginFilter
 *     2.禁用掉session会话管理，因为我们已经禁用session使用token了
 **/

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    ISystemUser iSystemUser;

    @Resource
    private DataSource dataSource;

    @Resource
    private RsaKeyProperties rsaKeyProperties;
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.rememberMe()
                .tokenValiditySeconds(60)
                .tokenRepository(persistentTokenRepository());
        //添加登录拦截器
        http.addFilter(new JwtLoginFilter(super.authenticationManager(),rsaKeyProperties));
        //添加认证返回的拦截器
        http.addFilter(new JwtVerifyFilter(super.authenticationManager(),rsaKeyProperties));
        //禁用session管理
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iSystemUser)
                .passwordEncoder(new BCryptPasswordEncoder());//添加加密类型
    }

    //注入持久化token的组件
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}