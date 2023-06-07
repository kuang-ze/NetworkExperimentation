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
 * @TableName reply
 */
@TableName(value ="reply")
@Data
public class Reply implements Serializable {
    /**
     * 回复ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 讨论内容
     */
    private String content;

    /**
     * 发表者ID
     */
    private Long publisherID;

    /**
     * 所属回复
     */
    private Long belongReplyID;


    /**
     * 是否是直接的回复
     */
    private Integer isRoot;

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

    /**
     * 所属讨论
     */
    private Long belongDiscussionID;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}