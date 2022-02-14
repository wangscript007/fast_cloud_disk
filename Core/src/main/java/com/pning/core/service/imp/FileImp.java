package com.pning.core.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.core.mapper.FilePropertyMapper;
import com.pning.core.pojo.FileProperty;
import com.pning.core.service.IFilePropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author Pning
 * @Date 2021/12/29 15:36
 **/
@Service
public class FileImp implements IFilePropertyService {

    @Resource
    private FilePropertyMapper filePropertyMapper;

    @Override
    public int insert(FileProperty fileProperty) {
        return filePropertyMapper.insert(fileProperty);
    }

    @Override
    public int deleteById(int id) {
        return filePropertyMapper.deleteById(id);
    }

    @Override
    public int updateById(FileProperty fileProperty) {
        return filePropertyMapper.updateById(fileProperty);
    }

    @Override
    public FileProperty selectById(int id) {
        return filePropertyMapper.selectById(id);
    }

    @Override
    public IPage<FileProperty> pageList(Page page, QueryWrapper<FileProperty> queryWrapper) {
        return filePropertyMapper.selectPage(page,queryWrapper);
    }
}
