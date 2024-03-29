package com.edu.networkexperimentation.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName discussion
 */
@TableName(value ="discussion")
@Data
public class Discussion implements Serializable {
    /**
     * 讨论ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 讨论标题
     */
    private String title;

    /**
     * 讨论内容
     */
    private String content;

    /**
     * 发表者ID
     */
    private Long publisherID;

    /**
     * 发表者名称
     */
    private String publisherName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}