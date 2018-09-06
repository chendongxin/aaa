package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.admin.dao.SysRoleUsableDao;
import com.hqjy.mustang.admin.model.entity.SysRoleUsableEntity;
import com.hqjy.mustang.admin.service.SysRoleUsableService;
import com.hqjy.mustang.admin.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : heshuangshuang
 * @date : 2018/7/16 17:29
 */
@Service
public class SysRoleUsableServiceImpl extends BaseServiceImpl<SysRoleUsableDao, SysRoleUsableEntity, Long> implements SysRoleUsableService {

    /**
     * 查询role可使用角色
     */
    @Override
    public List<SysRoleUsableEntity> findByRoleId(Long roleId) {
        return baseDao.findByRoleId(roleId);
    }

    /**
     * 可使用角色关系
     */
    @Override
    public int saveOrUpdate(Long roleId, List<Long> usableIdList) {
        // 删除原来关系
        int count = deleteByRoleId(roleId);
        if (usableIdList.size() > 0) {
            //保存新的关系
            count += baseDao.save(roleId, usableIdList, ShiroUtils.getUserId());
        }
        return count;
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return baseDao.deleteByRoleId(roleId);
    }

    /**
     * 获取可使用角色的角色关系
     */
    @Override
    public List<SysRoleUsableEntity> getListByUsableId(List<Long> usableIdList) {
        return baseDao.findListByUsableId(usableIdList);
    }

    /**
     * 获取可使用角色的角色Id
     */
    @Override
    public List<Long> getIdListByUsableId(List<Long> usableIdList) {
        return getListByUsableId(usableIdList).stream().map(SysRoleUsableEntity::getRoleId).collect(Collectors.toList());
    }
}
