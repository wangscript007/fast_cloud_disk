package com.pning.core.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.core.mapper.UserFileMapper;
import com.pning.core.pojo.UserFile;
import com.pning.core.service.IUserFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @Author Pning
 * @Date 2021/12/30 14:25
 **/
@Service
public class UserFileServiceImpl implements IUserFileService {

	@Resource
	private UserFileMapper userFileMapper;


	@Override
	public int insert(UserFile userFile) {
		if (userFile != null) {
            return userFileMapper.insert(userFile);
        }
		return 0;
	}


	@Override
	public int deleteById(int id) {
		return userFileMapper.deleteById(id);
	}

    @Override
    public int delete(Map<String,Object> map) {
        return userFileMapper.deleteByMap(map);
    }


    @Override
	public int updateById(UserFile userFile) {
		return userFileMapper.updateById(userFile);
	}


	@Override
	public UserFile selectById(int id) {
		return userFileMapper.selectById(id);
	}

    @Override
    public IPage<UserFile> pageList(Page page, QueryWrapper<UserFile> queryWrapper) {
        return userFileMapper.selectPage(page, queryWrapper);
    }


}