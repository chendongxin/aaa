package com.hqjy.transfer.crm.modules.sys.service.impl;

import com.hqjy.transfer.common.base.base.BaseServiceImpl;
import com.hqjy.transfer.crm.modules.sys.dao.SysUserRoleDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysUserRoleEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysUserRoleService;
import com.hqjy.transfer.crm.common.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRoleEntity, Long> implements SysUserRoleService {

    /**
     * 根绝角色编号查询用户编号列表
     */
    @Override
    public List<Long> getUserIdByRoleId(Long roleId) {
        return baseDao.findUserIdByRoleId(roleId);
    }

    /**
     * 保存用户角色关系
     */
    @Override
    public int save(Long userId, List<Long> roleIdList) {
        //先删除用户角色关系
        int count = baseDao.deleteByUserId(userId);
        if (roleIdList.size() == 0) {
            return count;
        }
        //保存角色与菜单关系
        SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setRoleIdList(roleIdList);
        userRoleEntity.setCreateId(ShiroUtils.getUserId());
        return baseDao.save(userRoleEntity);
    }

    /**
     * 根据用户ID，获取角色ID列表
     */
    @Override
    public List<Long> getRoleIdList(Long userId) {
        return baseDao.findRoleIdList(userId);
    }

    /**
     * 根据用户ID，获取角色列表
     */
    @Override
    public List<SysRoleEntity> getRoleList(Long userId) {
        return baseDao.findRoleList(userId);
    }

    /**
     * 删除用户与角色关系
     */
    @Override
    public int deleteByUserId(Long userId) {
        return baseDao.deleteByUserId(userId);
    }

}
