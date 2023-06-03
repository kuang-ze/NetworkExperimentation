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
 * @TableName section
 */
@TableName(value ="section")
@Data
public class Section implements Serializable {
    /**
     * 小节ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 小节标题
     */
    private String title;

    /**
     * 所属章节
     */
    private Long belongChapterID;


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