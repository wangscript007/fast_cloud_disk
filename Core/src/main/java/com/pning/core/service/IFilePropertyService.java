package com.pning.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.core.pojo.FileProperty;

/**
 * @Author Pning
 * @Date 2021/12/29 15:36
 **/
public interface IFilePropertyService {

    /**
     * 新增
     * @param fileProperty
     * @return
     */
    int insert(FileProperty fileProperty);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(int id);

    /**
     * 更新
     * @param fileProperty
     * @return
     */
    int updateById(FileProperty fileProperty);

    /**
     * 根据主键 id 查询
     * @param id
     * @return
     */
    FileProperty selectById(int id);

    /**
     * 分页查询
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<FileProperty> pageList(Page page, QueryWrapper<FileProperty> queryWrapper);

}
