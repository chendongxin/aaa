package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.dto.UserMenuDTO;
import com.hqjy.mustang.admin.model.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_menu 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysMenuEntity, Long> {
    /**
     * 查询用户所有授权菜单列表
     */
    List<UserMenuDTO> findUserMenuList(@Param("userId") Long userId);

    /**
     * 查询所有菜单列表
     */
    List<SysMenuEntity> findAllMenuList(@Param("userId") Long userId, @Param("types") Integer[] types);

    /**
     * 查询菜单的子资源
     */
    List<SysMenuEntity> findMenuByParentIds(Long[] parentIds);

    /**
     * 根据多个菜单编号查询菜单列表
     */
    List<SysMenuEntity> findListByMenuId(Long[] parentIds);
}