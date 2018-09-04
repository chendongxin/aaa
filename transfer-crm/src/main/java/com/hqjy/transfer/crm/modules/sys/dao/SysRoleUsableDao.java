package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleUsableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_role_usable 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/07/16 17:23
 */
@Mapper
public interface SysRoleUsableDao extends BaseDao<SysRoleUsableEntity, Long> {

    /**
     * 查询role可使用角色
     */
    List<SysRoleUsableEntity> findByRoleId(Long roleId);

    int deleteByRoleId(Long roleId);

    int save(@Param("roleId") Long roleId, @Param("usableIdList") List<Long> usableIdList, @Param("createId") Long createId);

    /**
     * 获取可使用角色的角色关系
     */
    List<SysRoleUsableEntity> findListByUsableId(@Param("usableIdList") List<Long> usableIdList);


}