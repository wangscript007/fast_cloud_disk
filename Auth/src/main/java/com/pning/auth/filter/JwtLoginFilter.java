package com.pning.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pning.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.pning.common.properties.RsaKeyProperties;
import com.pning.auth.pojo.SystemRole;
import com.pning.auth.pojo.SystemUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 * 继承UsernamePasswordAuthenticationFilter：
 *      1:重写认证方法：原本认证信息是从post表单获取，现在要从异步请求中获取
 *      2:信息原本认证后存放在session中，现在要存放在token中
 **/
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RsaKeyProperties rsaKeyProperties;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SystemUser systemUser = new ObjectMapper().readValue(request.getInputStream(), SystemUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(systemUser.getUsername(), systemUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        }catch (Exception e) {
            //e.printStackTrace();
            //返回错误信息
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401异常
                PrintWriter out = response.getWriter();
                Map resultMap = new HashMap();
                resultMap.put("code","401");
                resultMap.put("msg","用户名或密码错误");
                String message = new ObjectMapper().writeValueAsString(resultMap);//Map转字符串
                out.write(message);
            }catch (Exception outExection){
                outExection.printStackTrace();
            }
            //throw  new RuntimeException(e);
            return null;
        }
    }

    //2.重写的UsernamePasswordAuthenticationFilter父类AbstractAuthenticationProcessingFilter的successfulAuthentication方法，即用于认证成功后发送给客户端的信息操作
    //原本把信息放入session，现在要放入token中
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SystemUser user = new SystemUser();//不能给密码，会被祭天的
        user.setUsername(authResult.getName());
        user.setSystemroles((List<SystemRole>)authResult.getAuthorities());
        //私钥生成token，过期时间单位:分钟
        String token = JwtUtils.generateTokenExpireInMinutes(user,rsaKeyProperties.getPrivateKey(),24*60);
        //返回token给用户
        response.addHeader("Authorization", "Bearer " + token);
        //解决跨域导致前端获取不到token的问题
        response.setHeader("Access-Control-Expose-Headers","Authorization");
        try {
            //登录成功時，返回json格式进行提示
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);  //成功代号：200
            PrintWriter out = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_OK);     //成功代号：200
            map.put("message", "登陆成功！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
