package com.pning.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.core.pojo.UserFile;

import java.util.Map;

/**
 * @Author Pning
 * @Date 2021/12/30 14:25
 **/
public interface IUserFileService {

    /**
     * 新增
     * @param userFile
     * @return
     */
    int insert(UserFile userFile);

    /**
     * 通过id删除
     * @param id
     * @return
     */
    int deleteById(int id);

    /**
     * 条件删除
     * @param map
     * @return
     */
    int delete(Map<String,Object> map);

    /**
     * 更新
     * @param userFile
     * @return
     */
    int updateById(UserFile userFile);

    /**
     * 根据主键 id 查询
     * @param id
     * @return
     */
    UserFile selectById(int id);

    /**
     *分页查询
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<UserFile> pageList(Page page, QueryWrapper<UserFile> queryWrapper);

}