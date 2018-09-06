package com.hqjy.mustang.admin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user_role 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:53
 */
@Data
public class SysUserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 人员编号 user_id
     **/
    private Long userId;

    /**
     * 角色编号 role_id
     **/
    private Long roleId;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 角色Id列表
     */
    private List<Long> roleIdList;
}