package com.hqjy.transfer.crm.modules.sys.service.impl;

import com.hqjy.transfer.common.base.base.BaseServiceImpl;
import com.hqjy.transfer.common.base.constant.SystemId;
import com.hqjy.transfer.common.base.exception.RRException;
import com.hqjy.transfer.common.base.utils.RecursionUtil;
import com.hqjy.transfer.crm.modules.sys.dao.SysMenuDao;
import com.hqjy.transfer.crm.modules.sys.model.dto.UserMenuDTO;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysMenuEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysCacheService;
import com.hqjy.transfer.crm.modules.sys.service.SysDeleteService;
import com.hqjy.transfer.crm.modules.sys.service.SysMenuService;
import com.hqjy.transfer.crm.modules.sys.service.SysRoleMenuService;
import com.hqjy.transfer.crm.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author : heshuangshuang
 * @date : 2018/3/23 17:24
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity, Long> implements SysMenuService {

    @Autowired
    private SysDeleteService sysDeleteService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysCacheService sysCacheService;

    /**
     * 获取用户菜单List
     */
    @Override
    public List<UserMenuDTO> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (SystemId.SUPER_ADMIN.equals(userId)) {
            return baseDao.findUserMenuList(null);
        }
        return baseDao.findUserMenuList(userId);
    }

    @Override
    public Set<String> getUserMenuNameList(Long userId) {
        return getUserMenuList(userId).stream().map(UserMenuDTO::getName).collect(Collectors.toSet());
    }

    /**
     * 获取用户菜单Tree
     */
    @Override
    public List<UserMenuDTO> getUserMenuTree(Long userId) {
        //用户菜单树
        return recursionMenu(SystemId.TREE_ROOT, new CopyOnWriteArrayList<>(getUserMenuList(userId)));
    }

    /**
     * 将菜单列表递归成树
     */
    private List<UserMenuDTO> recursionMenu(Long parentId, List<UserMenuDTO> list) {
        List<UserMenuDTO> result = new ArrayList<>();
        list.forEach(value -> {
            if (parentId.equals(value.getParentId())) {
                value.setChildren(recursionMenu(value.getMenuId(), list));
                result.add(value);
                list.remove(value);
            }
        });
        return result;
    }

    /**
     * 获取所有菜单数据，包含树数据和列表
     */
    @Override
    public HashMap<String, List<SysMenuEntity>> getRecursionTree(Integer... type) {
        Integer[] types = type.clone();
        return RecursionUtil.listTree(true, SysMenuEntity.class, "getMenuId",
                baseDao.findAllMenuList(null, types),
                new ArrayList<>(Collections.singletonList(SystemId.TREE_ROOT)));
    }

    /**
     * 保存菜单
     */
    @Override
    public int save(SysMenuEntity menuPO) {
        SysMenuEntity newMenu = new SysMenuEntity();
        newMenu.setMenuName(menuPO.getMenuName());
        newMenu.setParentId(menuPO.getParentId());
        newMenu.setCode(menuPO.getCode());
        newMenu.setIcon(menuPO.getIcon());
        newMenu.setCreateId(ShiroUtils.getUserId());
        newMenu.setPerms(menuPO.getPerms());
        newMenu.setSeq(menuPO.getSeq());
        newMenu.setStatus(menuPO.getStatus());
        newMenu.setType(menuPO.getType());
        return baseDao.save(newMenu);
    }

    /**
     * 修改菜单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysMenuEntity menuPO) {
        SysMenuEntity newMenu = new SysMenuEntity();
        newMenu.setMenuId(menuPO.getMenuId());
        newMenu.setMenuName(menuPO.getMenuName());
        newMenu.setParentId(menuPO.getParentId());
        newMenu.setCode(menuPO.getCode());
        newMenu.setIcon(menuPO.getIcon());
        newMenu.setPerms(menuPO.getPerms());
        newMenu.setSeq(menuPO.getSeq());
        newMenu.setStatus(menuPO.getStatus());
        newMenu.setType(menuPO.getType());
        newMenu.setUpdateId(ShiroUtils.getUserId());
        int count = baseDao.update(newMenu);
        if (count > 0) {
            sysCacheService.cleanPerm();
        }
        return count;
    }

    /**
     * 删除菜单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] menuIds) {
        //删除菜单前，需要查询此菜单的关联资源
        //检查菜单是否存在子节点
        List<SysMenuEntity> children = baseDao.findMenuByParentIds(menuIds);
        if (children.size() > 0) {
            throw new RRException("菜单存在子资源，不能删除");
        }
        //删除角色菜单关系
        sysRoleMenuService.deleteByMenuIds(menuIds);

        //记录删除日志
        sysDeleteService.saveLogs(SysMenuEntity.class.getSimpleName(), baseDao.findListByMenuId(menuIds), "删除菜单");

        int count = baseDao.deleteBatch(menuIds);
        if (count > 0) {
            sysCacheService.cleanPerm();
        }
        return count;
    }
}
