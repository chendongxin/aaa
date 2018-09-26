package com.hqjy.mustang.common.model.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_dept 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/14 14:38
 */
@Data
public class SysDeptInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门编号 dept_id
     **/
    private Long deptId;

    /**
     * 父编号 parent_id
     **/
    private Long parentId;

    /**
     * 部门名称 dept_name
     **/
    private String deptName;

    /**
     * 排序号 seq
     **/
    private Integer seq;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 权重值 商机分配算法需要的 weights
     **/
    private Integer weights;

    /**
     * 状态 0正常 1禁用 status
     **/
    private Integer status;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 父部门名称
     */
    private String parentName;

}