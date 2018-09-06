package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.dto.UserMenuDTO;
import com.hqjy.mustang.admin.model.entity.SysMenuEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author : heshuangshuang
 * @date : 2018/3/23 17:23
 */
public interface SysMenuService extends BaseService<SysMenuEntity, Long> {

    /**
     * 获取用户菜单List
     */
    List<UserMenuDTO> getUserMenuList(Long userId);

    /**
     * 菜单code
     */
    Set<String> getUserMenuNameList(Long userId);

    /**
     * 获取用户菜单Tree
     */
    List<UserMenuDTO> getUserMenuTree(Long userId);

    /**
     * 获取所有菜单数据，包含树数据和列表
     */
    HashMap<String, List<SysMenuEntity>> getRecursionTree(Integer... type);


}
