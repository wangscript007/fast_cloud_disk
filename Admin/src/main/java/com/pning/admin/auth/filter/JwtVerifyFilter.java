package com.pning.admin.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pning.admin.auth.pojo.SystemUser;
import com.pning.common.pojo.Payload;
import com.pning.common.properties.RsaKeyProperties;
import com.pning.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 * jwt的认证拦截器
 **/
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties properties) {
        super(authenticationManager);
        this.rsaKeyProperties = properties;
    }

    /**
     * 重写拦截的验证方法
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  {
        try {
            //请求体的头中是否包含Authorization
            String header = request.getHeader("Authorization");
            //Authorization中是否包含Bearer，不包含直接返回
            if (header == null || !header.toLowerCase().startsWith("bearer ")) {//比较的时候转小写在比较
                chain.doFilter(request, response);//(未登录转态)交给SpringSecurity的后续过滤器
                responseJson(response);//发送未登录提示
                return;
            }
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);//通过token，获取用户信息
            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);//获取当前登录用户的信息
            chain.doFilter(request, response);//交给SpringSecurity的后续过滤器
        }catch (ServletException e){
            responseJson(response);
            e.printStackTrace();
        }catch (IOException e){
            responseJson(response);
            e.printStackTrace();
        } catch (Exception e) {
            responseJson(response);
            e.printStackTrace();
        }
    }

    /**
     * 未登录时给客户端的提示
     * @param response
     */
    private void responseJson(HttpServletResponse response) {
        try {
            //未登录提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);//权限不足代号：403
            PrintWriter out = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_FORBIDDEN);//权限不足代号：403
            map.put("message", "请登录！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通过token，获取用户信息
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");//获取token
        if (token != null) {
            //通过token解析出载荷信息
            Payload<SystemUser> payload = JwtUtils.getInfoFromToken(token.replace("Bearer ", ""), rsaKeyProperties.getPublicKey(), SystemUser.class);
            SystemUser user = payload.getUserInfo();
            //不为null，返回
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
                //token并没有密码，暂时也不设定验证码，所以设为null
            }
            return null;
        }
        return null;
    }
}
