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
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 问题id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问题题干
     */
    private String title;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 选项一
     */
    private String choice1;

    /**
     * 选项二
     */
    private String choice2;

    /**
     * 选项三
     */
    private String choice3;

    /**
     * 选项四
     */
    private String choice4;

    /**
     * 判断题正确答案
     */
    private Integer isTrue;

    /**
     * 选择题正确答案
     */
    private Integer correctChoice;

    /**
     * 填空题正确答案
     */
    private String correctContent;

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