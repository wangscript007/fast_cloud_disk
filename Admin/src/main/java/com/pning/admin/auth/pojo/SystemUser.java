package com.pning.admin.auth.pojo;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser implements UserDetails {
    private String username;
    private String password;
    private List<SystemRole> systemroles;

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<SystemRole> getSystemroles() {
        return systemroles;
    }

    public void setSystemroles(List<SystemRole> systemroles) {
        this.systemroles = systemroles;
    }

    //复写父类方法
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
//实现接口
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return systemroles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
