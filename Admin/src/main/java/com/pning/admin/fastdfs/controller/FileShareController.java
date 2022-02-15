package com.pning.admin.fastdfs.controller;

import com.pning.core.pojo.UserFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * @Author Pning
 * @Date 2022/2/15 10:39
 * 文件共享
 **/
public class FileShareController {
    /**
     * 文件共享
     * @param file_id
     * @return
     */
    @RolesAllowed(value = {"file:share"})
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
