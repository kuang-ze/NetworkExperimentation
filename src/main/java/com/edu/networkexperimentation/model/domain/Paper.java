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
 * @TableName paper
 */
@TableName(value ="paper")
@Data
public class Paper implements Serializable {
    /**
     * 试卷ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 年级试卷
     */
    private String title;

    /**
     * 出卷者
     */
    private Long userID;

    /**
     * 是否已经被回答
     */
    private Integer isAnswered;

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