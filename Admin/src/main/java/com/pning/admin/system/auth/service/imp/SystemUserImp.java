package com.pning.admin.system.auth.service.imp;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pning.admin.system.auth.mapper.SystemUserMapper;
import com.pning.admin.system.auth.service.ISystemUser;

import javax.annotation.Resource;

@Service
public class SystemUserImp implements ISystemUser {

    @Resource
    SystemUserMapper systemUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return systemUserMapper.selectSystemUserByUserName(username);
    }
}
