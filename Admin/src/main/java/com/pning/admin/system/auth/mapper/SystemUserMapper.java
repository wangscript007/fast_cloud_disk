package com.pning.admin.system.auth.mapper;

/**
 * @Author Pning
 * @Date 2021/12/10 9:13
 **/

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.pning.admin.system.auth.pojo.SystemUser;


@Mapper
@Repository
public interface SystemUserMapper {
   SystemUser selectSystemUserByUserName(@Param("username") String username);
}
