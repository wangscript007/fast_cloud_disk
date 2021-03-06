package com.pning.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * @Author Pning
 * @Date 2021-12-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 上传用户名
     */
    @TableField("username")
    private String username;
    /**
     * 上传时间
     */
    @TableField("upload_time")
    private DateTime uploadTime;

    /**
     * 列表查询使用的时间范围
     */
    @TableField(exist = false)
    private DateTime startTime;
    @TableField(exist = false)
    private DateTime endTime;

    /**
     * 列表查询使用的文件大小范围
     */
    @TableField(exist = false)
    private long startSize;
    @TableField(exist = false)
    private long endSize;

    public FileProperty(Integer fileId, String fileGroup, String filePath, String fileExtname, Long fileSize, String username, DateTime uploadTime) {
        this.fileId = fileId;
        this.fileGroup = fileGroup;
        this.filePath = filePath;
        this.fileExtname = fileExtname;
        this.fileSize = fileSize;
        this.username = username;
        this.uploadTime = uploadTime;
    }
    public FileProperty(Integer fileId, String fileGroup, String filePath, String fileExtname, String username) {
        this.fileId = fileId;
        this.fileGroup = fileGroup;
        this.filePath = filePath;
        this.fileExtname = fileExtname;
        this.username = username;
    }

}