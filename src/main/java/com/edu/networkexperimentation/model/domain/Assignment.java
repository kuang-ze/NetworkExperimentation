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
 * @TableName assignment
 */
@TableName(value ="assignment")
@Data
public class Assignment implements Serializable {
    /**
     * 作业ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业文件-真实路径
     */
    private String content;

    /**
     * 所属班级
     */
    private Long gradeID;

    /**
     * 截止日期
     */
    private Date deadline;

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