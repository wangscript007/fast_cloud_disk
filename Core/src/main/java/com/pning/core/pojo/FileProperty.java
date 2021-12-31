package com.pning.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Pning
 * @Date 2021-12-30
 */
@Data
@AllArgsConstructor
@TableName("file_property")
public class FileProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 文件id
    */
    @TableId(value = "file_id",type = IdType.AUTO)
    private Integer fileId;

    /**
    * 文件归属组
    */
    @TableField("file_group")
    private String fileGroup;

    /**
    * 文件路径
    */
    @TableField("file_path")
    private String filePath;

    /**
    * 文件后缀
    */
    @TableField("file_extname")
    private String fileExtname;

    /**
    * 文件大小
    */
    @TableField("file_size")
    private Long fileSize;

    public FileProperty(String fileGroup, String filePath, String fileExtname, Long fileSize) {
        this.fileGroup = fileGroup;
        this.filePath = filePath;
        this.fileExtname = fileExtname;
        this.fileSize = fileSize;
    }
}