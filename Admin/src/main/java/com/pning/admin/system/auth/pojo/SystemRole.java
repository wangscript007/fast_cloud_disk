package com.pning.admin.system.auth.pojo;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemRole implements GrantedAuthority {
    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
