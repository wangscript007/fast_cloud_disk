package com.pning.admin.fastdfs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.admin.fastdfs.utlits.UserUtil;
import com.pning.common.pojo.R;
import com.pning.core.pojo.FileProperty;
import com.pning.core.pojo.UserFile;
import com.pning.core.service.IUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Pning
 * @Date 2022/2/15 9:24
 * 文件映射
 **/
@RestController
@RequestMapping("/mapping")
public class FileMappingController {
    @Autowired
    IUserFileService iUserFileService;

    /**
     * 获取当前用户与文件映射列表
     * @param page
     * @param userFile
     * @return
     */
    @RolesAllowed(value = {"mapping:list"})
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestPart(name = "page", required = false) Page page,
                       @RequestPart(name = "userFile", required = false) UserFile userFile) {
        if(page==null){
            page = new Page(1,10);//默认第1页，一页10条
        }
        return R.ok(iUserFileService.pageList(page,userFile));
    }


    /**
     * 删除用户文件关联
     * @param file_id
     * @return
     */
    @RolesAllowed(value = {"mapping:delete"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteMapping(@RequestParam int file_id) {
        try {
            //删除用户和文件之间的关联，并不删除真实文件
            Map<String,Object> map = new HashMap<>();
            map.put("file_id",file_id);
            map.put("username", UserUtil.getUserName());
            iUserFileService.delete(map);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("映射删除失败" );
        }
    }
    /**
     * 更新用户文件关联
     * @param userFile
     * @return
     */
    @RolesAllowed(value = {"mapping:update"})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateMapping(@RequestParam UserFile userFile) {
        try {
            if(userFile==null){
                return R.fail("映射更新失败");
            }
            iUserFileService.updateById(userFile);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("映射更新失败" );
        }
    }
}
