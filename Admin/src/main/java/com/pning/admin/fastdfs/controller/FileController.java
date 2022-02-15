package com.pning.admin.fastdfs.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pning.admin.fastdfs.utlits.UserUtil;
import com.pning.common.pojo.R;
import com.pning.common.utils.ApiReturnUtil;
import com.pning.common.utils.FIlePropertieUtil;
import com.pning.common.utils.FastDFSUtil;
import com.pning.core.pojo.FileProperty;
import com.pning.core.pojo.UserFile;
import com.pning.core.service.IFilePropertyService;
import com.pning.core.service.IUserFileService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * @Author Pning
 * @Date 2021/12/30 14:25
 * 文件存储（包含上传下载服务）
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    IFilePropertyService iFilePropertyService;


    @Autowired
    IUserFileService iUserFileService;
    /**
     * 获取真实文件存储列表
     * @param page
     * @param fileProperty
     * @return
     */
    @RolesAllowed(value = {"file:list"})
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestPart(name = "page", required = false) Page page,
                       @RequestPart(name = "fileProperty", required = false)FileProperty fileProperty) {
        if(page==null){
            page = new Page(1,10);//默认第1页，一页10条
        }
        return R.ok(iFilePropertyService.pageList(page, fileProperty));
    }

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
                return R.fail("文件为空");
            } else {
                //文件后缀名
                String fileExtName = FIlePropertieUtil.getfileExtName(file.getOriginalFilename());
                //文件上传
                String[] filePro = FastDFSUtil.upload(file.getBytes(), fileExtName, null);
                //文件信息记录
                DateTime dateTime = new DateTime();
                FileProperty fileProperty = new FileProperty(null,filePro[0], filePro[1], fileExtName, file.getSize(), UserUtil.getUserName(),dateTime);
                iFilePropertyService.insert(fileProperty);
                //文件映射信息记录
                UserFile userFile = new UserFile(ufPid, UserUtil.getUserName(), fileProperty.getFileId(),file.getOriginalFilename(), "".equals(fileExtName) ? 0 : 1,dateTime);
                iUserFileService.insert(userFile);
                return R.ok();
            }
        } catch (Exception e) {
            return R.fail("上传错误" );
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
            //删除用户和文件之间的关联（后续需求：不能让用户完全不知道的情况下文件被删除，留下个映射让用户以为文件还在，但是下载会失败）
//            Map<String,Object> map = new HashMap<>();
//            map.put("file_id",file_id);
//            map.put("username",UserUtil.getUserName());
//            iUserFileService.delete(map);
            //删除文件信息
            iFilePropertyService.deleteById(file_id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("删除失败" );
        }
    }

    /**
     * 文件下载
     * @param file_id
     * @return
     */
    @RolesAllowed(value = {"file:download"})
    @PermitAll
    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam int file_id) {
        //后期添加一个用户是否有该文件的判断
        try {
            FileProperty fileProperty = iFilePropertyService.selectById(file_id);
            if(fileProperty==null){
                //文件不存在（应该为真实文件已经被删除只存在映射的情况）
                return null;
            }
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



}

