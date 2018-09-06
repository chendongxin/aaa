package com.hqjy.mustang.admin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_dict 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/19 14:31
 */
@Data
public class SysDictEntity implements Serializable {

    private Long id;

    /**
     * 字典编码 code
     **/
    private String code;

    /**
     * 字典键 value
     **/
    private String value;

    /**
     * 字典显示值 label
     **/
    private String label;

    /**
     * 排序号 seq
     **/
    private Integer seq;

    /**
     * 是否是默认 1：默认 def
     **/
    private Integer def;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 更新人 update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}