package com.hqjy.mustang.admin.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_menu 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@Data
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号 menu_id
     **/
    private Long menuId;

    /**
     * 类型 0菜单 1权限 type
     **/
    @Min(value = 0, message = "菜单类型不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer type;

    /**
     * 父编号 parent_id
     **/
    @Min(value = 0, message = "父级菜单不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long parentId;

    /**
     * 菜单名称 menu_name
     **/
    @NotBlank(message = "菜单名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String menuName;

    /**
     * 菜单编码 code
     **/
    private String code;

    /**
     * 权限 perms
     **/
    private String perms;

    /**
     * 菜单图标 icon
     **/
    private String icon;

    /**
     * 排序号 seq
     **/
    @Min(value = 0, message = "排序号不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long seq;

    /**
     * 状态 0正常 1禁用 state
     **/
    @Min(value = 0, message = "菜单状态不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
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
     * 更新时间 update_time
     **/
    private Date updateTime;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 子菜单
     */
    private List<SysMenuEntity> children;

}