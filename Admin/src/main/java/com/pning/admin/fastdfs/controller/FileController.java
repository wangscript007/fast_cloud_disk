package com.pning.admin.fastdfs.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pning.admin.auth.pojo.SystemUser;
import com.pning.admin.fastdfs.utlits.UserUtil;
import com.pning.common.utils.ApiReturnUtil;
import com.pning.common.utils.FIlePropertieUtil;
import com.pning.common.utils.FastDFSUtil;
import com.pning.core.pojo.FileProperty;
import com.pning.core.pojo.UserFile;
import com.pning.core.service.IFilePropertyService;
import com.pning.core.service.IUserFileService;
import io.swagger.annotations.ApiModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    IFilePropertyService iFilePropertyService;

    @Autowired
    IUserFileService iUserFileService;

    /**
     * 文件上传
     * @param file  文件
     * @param ufPid 上传文件所在的上一级文件夹id
     * @return
     */
    @RolesAllowed(value = {"file:upload"})
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(@RequestParam MultipartFile file, @RequestParam int ufPid, HttpServletRequest request) {
        try {
            if (file.isEmpty()) {
                return ApiReturnUtil.error("文件为空");
            } else {
                //文件后缀名
                String fileExtName = FIlePropertieUtil.getfileExtName(file.getOriginalFilename());
                //文件上传
                String[] filePro = FastDFSUtil.upload(file.getBytes(), fileExtName, null);
                DateTime dateTime = new DateTime();
                //文件信息记录
                FileProperty fileProperty = new FileProperty(filePro[0], filePro[1], fileExtName, file.getSize(), UserUtil.getUserName(),dateTime);
                iFilePropertyService.insert(fileProperty);
                UserFile userFile = new UserFile(ufPid, UserUtil.getUserName(), fileProperty.getFileId(),file.getOriginalFilename(), "".equals(fileExtName) ? 0 : 1,dateTime);
                iUserFileService.insert(userFile);
                return ApiReturnUtil.success("上传成功");
            }
        } catch (Exception e) {
            return ApiReturnUtil.error("上传错误：" + e.getMessage());
        }
    }

    /**
     * 删除用户文件关联
     * @param file_id
     * @return
     */
    @RolesAllowed(value = {"file:deletelogic"})
    @RequestMapping(value = "/deletelogic", method = RequestMethod.POST)
    public Object deletelogic(@RequestParam int file_id) {
        try {
            //删除用户和文件之间的关联，并不删除真实文件
            Map<String,Object> map = new HashMap<>();
            map.put("file_id",file_id);
            map.put("username",UserUtil.getUserName());
            iUserFileService.delete(map);
            return ApiReturnUtil.success("删除成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return ApiReturnUtil.error("删除失败：" + e.getMessage());
        }
    }


    /**
     * 真实删除文件（管理员特有权限）
     * @param file_id
     * @return
     */
    @RolesAllowed(value = {"file:delete"})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam int file_id) {
        try {
            //删除文件
            FileProperty fileProperty = iFilePropertyService.selectById(file_id);
            FastDFSUtil.delete(fileProperty.getFileGroup(),fileProperty.getFilePath());
            //删除用户和文件之间的关联
            Map<String,Object> map = new HashMap<>();
            map.put("file_id",file_id);
            map.put("username",UserUtil.getUserName());
            iUserFileService.delete(map);
            //删除文件信息
            iFilePropertyService.deleteById(file_id);
            return ApiReturnUtil.success("文件删除成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return ApiReturnUtil.error("上删除失败：" + e.getMessage());
        }
    }


    //@RolesAllowed(value = {"file:download"})
    @PermitAll
    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam int file_id) {
        //后期添加一个用户是否有该文件的判断
        try {
            FileProperty fileProperty = iFilePropertyService.selectById(file_id);
            byte[] file = FastDFSUtil.download(fileProperty.getFileGroup(), fileProperty.getFilePath());
            HttpHeaders headers = new HttpHeaders();
            //请求头信息
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //文件大小
            headers.setContentLength(fileProperty.getFileSize());
            //文件名
            headers.setContentDispositionFormData("attachment", System.currentTimeMillis()+"."+fileProperty.getFileExtname());
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(file,headers, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //@RolesAllowed(value = {"file:share"})
    @PermitAll
    @ResponseBody
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public UserFile share(@RequestParam int file_id) {
        try {

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

