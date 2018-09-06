package com.hqjy.mustang.admin.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_role 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:52
 */
@Data
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号 role_id
     **/
    private Long roleId;

    /**
     * 角色标识字符串 role_code
     **/
    private String roleCode;

    /**
     * 角色名称 role_name
     **/
    @NotBlank(message = "角色名称", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String roleName;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 状态 0正常 1禁用 status
     **/
    @Min(value = 0, message = "状态不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer status;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新日期 update_time
     **/
    private Date updateTime;

    /**
     * 角色拥有的菜单Id
     */
    private List<Long> menuIdList;

    /**
     * 角色拥有的菜单
     */
    private List<SysMenuEntity> menuList;

    /**
     * 可使用角色Id
     */
    private List<SysRoleEntity> usableList;
    private List<Long> usableIdList;
}