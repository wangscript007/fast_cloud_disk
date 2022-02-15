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
    public IPage<UserFile> pageList(Page page,UserFile userFile) {
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userFile.getUsername());//用户名查询
        if(userFile!=null) {
            if (userFile.getUfPid() != null && !"".equals(userFile.getUfPid())) {
                queryWrapper.eq("uf_pid", userFile.getUfPid());//查询指定目录id下的文件映射
            }
            if (userFile.getFileName() != null && !"".equals(userFile.getFileName())) {
                queryWrapper.like("file_name", userFile.getFileName());//文件名模糊
            }
            if (userFile.getStartTime() != null && userFile.getEndTime() != null) {
                queryWrapper.in("create_time", userFile.getStartTime(), userFile.getEndTime());//映射添加时间的范围查询
            }
        }
        return userFileMapper.selectPage(page, queryWrapper);
    }


}