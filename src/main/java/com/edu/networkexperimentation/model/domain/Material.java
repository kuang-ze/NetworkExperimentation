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
 * @TableName material
 */
@TableName(value ="material")
@Data
public class Material implements Serializable {
    /**
     * 资料ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 资料标题
     */
    private String title;

    /**
     * 资料真实路径
     */
    private String file;

    /**
     * 上传用户
     */
    private Long uploadUser;

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
     * 所属小节
     */
    private Long sectionID;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}