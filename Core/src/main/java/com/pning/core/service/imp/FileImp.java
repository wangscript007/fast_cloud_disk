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
    public IPage<FileProperty> pageList(Page page, FileProperty fileProperty) {
        QueryWrapper<FileProperty> queryWrapper = null;
        if(fileProperty!=null) {
            queryWrapper = new QueryWrapper<>();
            if (fileProperty.getFileId() != null && !"".equals(fileProperty.getFileId())) {
                queryWrapper.like("file_id", fileProperty.getFileId().toString());//拓展名
            }
            if (fileProperty.getFileExtname() != null && !"".equals(fileProperty.getFileExtname())) {
                queryWrapper.like("file_extname", fileProperty.getFileExtname());//拓展名
            }
            if (fileProperty.getFileGroup() != null && !"".equals(fileProperty.getFileGroup())) {
                queryWrapper.like("file_group", fileProperty.getFileGroup());//归属组
            }
            if (fileProperty.getStartTime() != null && fileProperty.getEndTime() != null) {
                queryWrapper.in("create_time", fileProperty.getStartTime(), fileProperty.getEndTime());//上传时间范围查询
            }
            if (fileProperty.getStartSize() >=0 && fileProperty.getEndSize() >=0 && fileProperty.getEndSize() >= fileProperty.getStartSize()){
                queryWrapper.gt("file_size", fileProperty.getStartSize())
                            .lt("file_size",  fileProperty.getEndSize());//文件大小范围查询
            }
            if (fileProperty.getUsername() != null && !"".equals(fileProperty.getUsername())) {
                queryWrapper.like("username", fileProperty.getUsername());//用户名模糊查询
            }

        }
        return filePropertyMapper.selectPage(page,queryWrapper);
    }
}
