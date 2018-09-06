package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysRoleDao;
import com.hqjy.mustang.admin.model.entity.SysRoleEntity;
import com.hqjy.mustang.admin.model.entity.SysRoleUsableEntity;
import com.hqjy.mustang.admin.service.*;
import com.hqjy.mustang.admin.utils.ShiroUtils;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRoleEntity, Long> implements SysRoleService {
    @Autowired
    private SysDeleteService sysDeleteService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysCacheService sysCacheService;
    @Autowired
    private SysRoleUsableService sysRoleUsableService;

    @Override
    public SysRoleEntity findOne(Long roleId) {
        SysRoleEntity role = super.findOne(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.findMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        // 可使用角色ID
        role.setUsableIdList(sysRoleUsableService.findByRoleId(roleId).stream().map(SysRoleUsableEntity::getUsableId).collect(Collectors.toList()));
        return role;
    }

    @Override
    public List<SysRoleEntity> findPage(PageQuery pageQuery) {
        List<SysRoleEntity> roleList = super.findPage(pageQuery);
        // 查询role可使用角色
        roleList.forEach(role -> {
            List<SysRoleUsableEntity> roleUsable = sysRoleUsableService.findByRoleId(role.getRoleId());
            if (roleUsable.size() > 0) {
                role.setUsableList(baseDao.findRoleListByRoleIdList(roleUsable.stream().map(SysRoleUsableEntity::getUsableId).collect(Collectors.toList())));
            }
        });
        return roleList;
    }

    /**
     * 角色下拉列表
     */
    @Override
    public List<SysRoleEntity> getDrop(boolean showAll) {
        List<Long> roleIdList = new ArrayList<>();
        //系统管理员，拥有最高权限
        if (showAll || SystemId.SUPER_ADMIN.equals(ShiroUtils.getUserId())) {
            roleIdList = baseDao.findAllRoleList().stream().map(SysRoleEntity::getRoleId).collect(Collectors.toList());
        } else {
            // 角色Id列表
            List<Long> userRoleIdList = sysUserRoleService.getRoleIdList(ShiroUtils.getUserId());
            if (userRoleIdList.size() > 0) {
                roleIdList = sysRoleUsableService.getIdListByUsableId(userRoleIdList);
            }
        }
        return baseDao.findDrop(roleIdList);
    }

    /**
     * 获取所有角色列表
     */
    @Override
    public List<SysRoleEntity> getAllRoleList() {
        return baseDao.findAllRoleList();
    }

    /**
     * 保存角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysRoleEntity role) {
        SysRoleEntity newRole = PojoConvertUtil.convert(role, SysRoleEntity.class);
        newRole.setCreateId(ShiroUtils.getUserId());
        int count = baseDao.save(newRole);
        if (count > 0) {
            //保存角色与菜单关系
            sysRoleMenuService.save(newRole.getRoleId(), newRole.getMenuList());
            sysCacheService.cleanRole();
            // 保存可使用角色关系
            sysRoleUsableService.saveOrUpdate(newRole.getRoleId(), newRole.getUsableIdList());
        }
        return count;
    }

    /**
     * 修改角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysRoleEntity role) {
        SysRoleEntity newRole = PojoConvertUtil.convert(role, SysRoleEntity.class);
        newRole.setUpdateId(ShiroUtils.getUserId());
        int roleCount = baseDao.update(newRole);
        //更新角色与菜单关系
        int menuCount = sysRoleMenuService.save(newRole.getRoleId(), newRole.getMenuList());
        // 保存可使用角色关系
        sysRoleUsableService.saveOrUpdate(newRole.getRoleId(), newRole.getUsableIdList());
        int count = roleCount + menuCount;
        if (count > 0) {
            //清空用户权限缓存
            sysCacheService.cleanRole();
            sysCacheService.cleanPerm();
        }
        return count;
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long roleId) {
        //检测是否有用户在使用此角色
        List<Long> list = sysUserRoleService.getUserIdByRoleId(roleId);
        if (list.size() > 0) {
            throw new RRException("角色 [ " + baseDao.findOne(roleId).getRoleName() + " ] 存在使用用户，不能被删除");
        }
        int count = baseDao.delete(roleId);

        if (count > 0) {
            //删除角色与菜单关系
            sysRoleMenuService.deleteByRoleId(roleId);
            // 删除角色可用角色关系
            sysRoleUsableService.deleteByRoleId(roleId);
            //记录删除日志
            sysDeleteService.saveLog(SysRoleEntity.class.getSimpleName(), baseDao.findOne(roleId), "删除角色");
            //清除权限缓存
            sysCacheService.cleanRole();
            sysCacheService.cleanPerm();
        }
        return count;
    }


}
