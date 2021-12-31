package com.pning.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;


/**
 * @Author Pning
 * @Date 2021-12-30
 */
@Data
@TableName("user_file")
public class UserFile implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * id
    */
    @TableId(value = "uf_id",type = IdType.AUTO)
    private Integer ufId;

    /**
    * 父级id
    */
    @TableField("uf_pid")
    private Integer ufPid;

    /**
    * 用户名
    */
    @TableField("username")
    private String username;

    /**
    * 文件id
    */
    @TableField("file_id")
    private Integer fileId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String file_name;

    /**
    * 文件类型（0：文件夹；1：文件）
    */
    @TableField("file_sort")
    private Integer fileSort;

    public UserFile(Integer ufPid, String username, Integer fileId,String file_name, Integer fileSort) {
        this.ufPid = ufPid;
        this.username = username;
        this.fileId = fileId;
        this.file_name = file_name;
        this.fileSort = fileSort;
    }
}