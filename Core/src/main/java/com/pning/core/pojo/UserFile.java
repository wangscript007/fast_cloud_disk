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
@NoArgsConstructor
@AllArgsConstructor
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
    private String fileName;

    /**
    * 文件类型（0：文件夹；1：文件）
    */
    @TableField("file_sort")
    private Integer fileSort;

    /**
     * 状态（0：可用；1：禁用）
     */
    @TableField("status")
    private Integer status;
    /**
     * 上传时间
     */
    @TableField("create_time")
    private DateTime createTime;

    /**
     * 列表查询使用的时间范围
     */
    @TableField(exist = false)
    private DateTime startTime;
    @TableField(exist = false)
    private DateTime endTime;

    public UserFile(Integer ufPid, String username, Integer fileId,String fileName, Integer fileSort, DateTime createTime) {
        this.ufPid = ufPid;
        this.username = username;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSort = fileSort;
        this.createTime = createTime;
    }
}