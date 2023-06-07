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
 * @TableName answer
 */
@TableName(value ="answer")
@Data
public class Answer implements Serializable {
    /**
     * 回答ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 回答类型
     */
    private String type;

    /**
     * 回答对应的题目
     */
    private Long questionID;

    /**
     * 回答对应的试卷
     */
    private Long paperID;

    /**
     * 回答内容
     */
    private String content;

    /**
     * 回答是否正确
     */
    private Integer isTrue;

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