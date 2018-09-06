package com.hqjy.mustang.admin.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户菜单实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 18:09
 */
@Data
public class UserMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
    private Long menuId;

    /**
     * 资源编码 code
     */
    private String name;

    /**
     * 菜单名字 menuName
     */
    private String title;

    /**
     * 上级菜单编号
     */
    @JSONField(serialize = false)
    private Long parentId;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<UserMenuDTO> children;
}
