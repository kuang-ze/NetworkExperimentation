package com.edu.networkexperimentation.model.domain;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import lombok.Data;

/**
 * 
 * @TableName times
 */
@TableName(value ="times")
@Data
public class Times implements Serializable {
    /**
     * 
     */
    @MppMultiId
    @TableField("stuid")
    private Integer stuid;

    /**
     * 
     */
    @MppMultiId
    @TableField("chapterid")
    private Integer chapterid;

    /**
     * 
     */
    @MppMultiId
    @TableField("interid")
    private Integer interid;

    /**
     * 
     */
    private Date checkin;

    /**
     * 
     */
    private Date checkout;

    /**
     * 
     */
    private Integer scan;

    /**
     * 
     */
    private Double time_length;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}