package com.hqjy.mustang.admin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user_dept 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/16 11:04
 */
@Data
public class SysUserDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 用户编号 user_id
     **/
    private Long userId;

    /**
     * 部门名称
     **/
    private String deptName;

    /**
     * 部门编号 dept_id
     **/
    private Long deptId;

    /**
     * 权重值 weights
     **/
    private Integer weights;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 部门Id列表
     */
    private List<Long> deptIdList;
}