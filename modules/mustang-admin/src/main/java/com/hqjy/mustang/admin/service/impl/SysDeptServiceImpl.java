package com.hqjy.mustang.admin.service.impl;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.admin.feign.AllotApiService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.admin.dao.SysDeptDao;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.service.SysCacheService;
import com.hqjy.mustang.admin.service.SysDeleteService;
import com.hqjy.mustang.admin.service.SysDeptService;
import com.hqjy.mustang.admin.service.SysUserDeptService;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;

/**
 * 部门管理
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptDao, SysDeptEntity, Long> implements SysDeptService {

    @Autowired
    private SysDeleteService sysDeleteService;
    @Autowired
    private SysUserDeptService sysUserDeptService;
    @Autowired
    private SysCacheService sysCacheService;
    @Autowired
    private AllotApiService allotApiService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取所有部门列表
     */
    @Override
    public List<SysDeptEntity> getAllDeptList() {
        return baseDao.findAllDeptList();
    }

    /**
     * 获取所有部门列表
     */
    @Override
    public List<Long> getAllDeptIdList() {
        return baseDao.findAllDeptIdList();
    }

    /**
     * 分页查询
     */
    @Override
    public List<SysDeptEntity> findPage(PageQuery pageQuery) {
        List<Long> allDeptIdList = sysUserDeptService.getUserAllDeptId(getUserId());
        pageQuery.put("allDeptId", allDeptIdList);
        return super.findPage(pageQuery);
    }

    /**
     * 部门管理树数据
     */
    @Override
    public HashMap<String, List<SysDeptEntity>> getRecursionTree(boolean showRoot) {
        List<Long> parentIdList = new ArrayList<>();
        //系统管理员，拥有最高权限
        if (SystemId.SUPER_ADMIN.equals(getUserId())) {
            parentIdList.add(SystemId.TREE_ROOT);
        } else {
            parentIdList = sysUserDeptService.getUserDeptId(getUserId());
        }
        return RecursionUtil.listTree(showRoot, SysDeptEntity.class, "getDeptId", getAllDeptList(), parentIdList);
    }

    @Override
    public Map<String, List<SysDeptEntity>> getUserDeptTree(Boolean isRoot, Boolean showRoot) {
        List<Long> parentIdList = new ArrayList<>();
        //系统管理员，拥有最高权限
        if (isRoot || SystemId.SUPER_ADMIN.equals(getUserId())) {
            parentIdList.add(SystemId.TREE_ROOT);
        } else {
            parentIdList = sysUserDeptService.getUserDeptId(getUserId());
        }
        return RecursionUtil.listTree(showRoot, SysDeptEntity.class, "getDeptId", getAllDeptList(), parentIdList);
    }

    @Override
    public Map<String, List<SysDeptEntity>> getSaleDeptTree(String deptName) {
        Long deptId = baseDao.getDeptByName(deptName);
        if (deptId == null) {
            throw new RRException("不存在名字为：[" + deptName + "]的部门");
        }
        List<Long> parentIdList = new ArrayList<>();
        parentIdList.add(deptId);
        return RecursionUtil.getTree(true, SysDeptEntity.class, "getDeptId", getAllDeptList(), parentIdList);
    }

    /**
     * 部门选择数据,排除状态为不为正常的组织
     */
    @Override
    public HashMap<String, List<SysDeptEntity>> getSelectTree(boolean showRoot) {
        List<Long> parentIdList = new ArrayList<>();
        //系统管理员，拥有最高权限
        if (SystemId.SUPER_ADMIN.equals(getUserId())) {
            parentIdList.add(SystemId.TREE_ROOT);
        } else {
            parentIdList = sysUserDeptService.getUserDeptId(getUserId());
        }
        return RecursionUtil.listTree(showRoot, SysDeptEntity.class, "getDeptId", baseDao.findValidDeptList(), parentIdList);
    }

    /**
     * 新增部门
     */
    @Override
    public int save(SysDeptEntity dept) {
        // 检测越权
        if (checkPermission(true, dept.getParentId())) {
            SysDeptEntity newDept = PojoConvertUtil.convert(dept, SysDeptEntity.class);
            newDept.setCreateId(getUserId());
            int count = super.save(newDept);
            if (count > 0) {
                sysCacheService.cleanDept();
                // 刷新部门分配算法
                allotApiService.restDeptList(newDept.getParentId());
            }
            return count;
        }
        throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
    }

    /**
     * 修改部门
     */
    @Override
    public int update(SysDeptEntity dept) {
        if (SystemId.TREE_ROOT.equals(dept.getDeptId())) {
            throw new RRException(StatusCode.DATABASE_UPDATE_ROOT);
        }
        // 检测越权
        if (checkPermission(true, dept.getParentId())) {
            SysDeptEntity oldDept = baseDao.findOne(dept.getDeptId());
            if (!oldDept.getParentId().equals(dept.getParentId())) {
                //检查部门是否存在子节点
                List<SysDeptEntity> children = baseDao.findDeptByParentId(dept.getDeptId());
                if (children.size() > 0) {
                    throw new RRException(StatusCode.DATABASE_UPDATE_CHILD);
                }
            }

            SysDeptEntity newDept = PojoConvertUtil.convert(dept, SysDeptEntity.class);
            newDept.setUpdateId(getUserId());

            int count = super.update(newDept);
            if (count > 0) {
                sysCacheService.cleanDept();
                // 修改部门需要刷新分配算法
                allotApiService.restDeptList(newDept.getParentId());
            }
            return count;
        }
        throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
    }

    /**
     * 删除部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long deptId) {

        if (deptId.equals(SystemId.TREE_ROOT)) {
            throw new RRException(StatusCode.DATABASE_DELETE_ROOT);
        }
        if (!checkPermission(true, deptId)) {
            throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
        }

        SysDeptEntity deptInfo = baseDao.findOne(deptId);

        if (deptInfo == null) {
            return 1;
        }

        //检查是否有人员在此部门下
        List<Long> list = sysUserDeptService.getUserIdByDeptId(deptId);
        if (list.size() > 0) {
            throw new RRException("部门 [ " + deptInfo.getDeptName() + " ] 存在用户，不能被删除");
        }

        // 检测是否有区域配置 TODO
      /*  List<CustAreaDeptEntity> areaDeptList = custAreaDeptService.findListByDeptId(deptId);
        if (areaDeptList.size() > 0) {
            throw new RRException("部门 [ " + deptInfo.getDeptName() + " ] 在区域下被配置，不能被删除");
        }*/

        //检查部门是否存在子节点
        List<SysDeptEntity> children = baseDao.findDeptByParentId(deptId);
        if (children.size() > 0) {
            throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
        }

        //删除人员与部门关系
        sysUserDeptService.deleteByDeptId(deptId);

        //清除部门资源（调用客户相关方法，将商机转到其他部门什么的） TODO

        //记录删除日志
        sysDeleteService.saveLog(SysDeptEntity.class.getSimpleName(), deptInfo, "删除部门");

        int count = baseDao.delete(deptId);
        if (count > 0) {
            sysCacheService.cleanDept();
            // 刷新部门分配算法
            allotApiService.restDeptList(deptInfo.getParentId());
        }
        return count;
    }

    /**
     * 检测是否越权，增加和修改的部门是否在当前用户的部门之下
     */
    @Override
    public boolean checkPermission(boolean userAllDept, Long deptId) {
        // 管理员直接通过
        if (SystemId.SUPER_ADMIN.equals(getUserId())) {
            return true;
        }
        List<Long> allDeptIdList = userAllDept ? sysUserDeptService.getUserAllDeptId(getUserId()) : sysUserDeptService.getUserSubDeptId(getUserId());
        for (Long id : allDeptIdList) {
            if (deptId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据部门id列表获取部门列表信息
     */
    @Override
    public List<SysDeptEntity> getListBydeptIdList(List<Long> deptIdList) {
        return baseDao.getListBydeptIdList(deptIdList);
    }

    @Override
    public List<SysDeptEntity> getDeptEntityByDeptIds(String deptIdList) {
        return baseDao.getDeptEntityByDeptIds(deptIdList);
    }

    @Override
    public List<Long> getAllDeptUnderDeptId(Long deptId) {
        List<Long> allDeptIdList = redisUtils.get(RedisKeys.Dept.allDept(deptId), new TypeToken<List<Long>>() {
        });
        if (allDeptIdList == null) {
            List<Long> parentIdList = new ArrayList<>();
            parentIdList.add(deptId);
            List<Long> list = new ArrayList<>();
            allDeptIdList = RecursionUtil.list(list, SysDeptEntity.class, "getDeptId", true, new CopyOnWriteArrayList<>(baseDao.findValidDeptList()), parentIdList);
            redisUtils.set(RedisKeys.Dept.allDept(deptId), allDeptIdList);
        }
        return allDeptIdList;
    }

    @Override
    public List<SysDeptEntity> getDeptEntityByDeptName(String deptName) {
        Long deptId = this.getDeptByName(deptName);
        if (deptId == null) {
            return null;
        }
        List<Long> allDeptUnderDeptId = this.getAllDeptUnderDeptId(deptId);
        if (allDeptUnderDeptId.size() == 0) {
            throw new RRException("部门编号：" + deptId + "不存在");
        }
        return this.getDeptEntityByDeptIds(deptIdListToString(allDeptUnderDeptId));
    }

    @Override
    public List<SysDeptEntity> getDeptEntityByDeptId(Long deptId) {
        List<Long> allDeptUnderDeptId = this.getAllDeptUnderDeptId(deptId);
        if (allDeptUnderDeptId.size() == 0) {
            throw new RRException("部门编号：" + deptId + "不存在");
        }
        return this.getDeptEntityByDeptIds(deptIdListToString(allDeptUnderDeptId));
    }

    @Override
    public List<SysDeptEntity> findValidDeptList() {
        return baseDao.findValidDeptList();
    }

    @Override
    public String deptIdListToString(List<Long> deptIdList) {
        List<String> ids = new ArrayList<>();
        deptIdList.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        return StringUtils.listToString(ids);
    }

    /**
     * 获取用户所在部门及子部门
     */
    @Override
    public List<SysDeptEntity> getUserDeptList(Long userId) {
        return baseDao.findDeptListByUserId(userId);
    }

    @Override
    public Long getDeptByName(String deptName) {
        return baseDao.getDeptByName(deptName);
    }
}
