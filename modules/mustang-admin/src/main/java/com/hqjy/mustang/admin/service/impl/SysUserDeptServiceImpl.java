package com.hqjy.mustang.admin.service.impl;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.admin.dao.SysUserDeptDao;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserDeptEntity;
import com.hqjy.mustang.admin.service.SysDeptService;
import com.hqjy.mustang.admin.service.SysUserDeptService;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 部门管理
 *
 * @author :heshuangshuang
 * @ date :2018/1/20 10:10
 */
@Service("sysUserDeptService")
@Slf4j
public class SysUserDeptServiceImpl extends BaseServiceImpl<SysUserDeptDao, SysUserDeptEntity, Long> implements SysUserDeptService {

    /**
     * 缓存时间10分钟 =  10 * 60 秒
     */
    private static final long DEPT_EXPIRE = 10 * 60;


    private RedisUtils redisUtils;
    private SysDeptService sysDeptService;


    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    /**
     * 查询用户部门列表
     */
    @Override
    public List<SysUserDeptEntity> getUserDeptList(Long userId) {
        return baseDao.findDeptListByUserId(userId);
    }

    /**
     * 删除用户部门关系
     */
    @Override
    public int deleteByUserId(Long userId) {
        return baseDao.deleteByUserId(userId);
    }

    /**
     * 保存用户部门关系
     */
    @Override
    public int save(Long userId, List<SysUserDeptEntity> userDeptList) {
        //先删除用户部门关系
        int count = baseDao.deleteByUserId(userId);
        if (userDeptList.size() == 0) {
            return count;
        }
        //保存部门与菜单关系
        return baseSave(userId, userDeptList);
    }

    @Override
    public int save(Long userId, List<SysUserDeptEntity> userDeptList, List<SysUserDeptEntity> noUserDeptList) {
        if (noUserDeptList.size() > 0) {
            int count = baseDao.deleteByUserIdeExclude(userId, noUserDeptList);
            if (userDeptList.size() == 0) {
                return count;
            }
            return baseSave(userId, userDeptList);
        }
        return save(userId, userDeptList);
    }

    private int baseSave(Long userId, List<SysUserDeptEntity> userDeptList) {
        int count = 0;
        //保存部门与菜单关系
        for (SysUserDeptEntity dept : userDeptList) {
            SysUserDeptEntity userDeptEntity = new SysUserDeptEntity();
            userDeptEntity.setUserId(userId);
            userDeptEntity.setDeptId(dept.getDeptId());
            userDeptEntity.setWeights(dept.getWeights());
            userDeptEntity.setCreateId(ShiroUtils.getUserId());
            count += baseDao.save(userDeptEntity);
        }
        return count;
    }


    /**
     * 查询用户部门Id列表,带缓存
     */
    @Override
    public List<Long> getUserDeptId(Long userId) {
        List<Long> deptList = redisUtils.get(RedisKeys.User.curdept(userId), new TypeToken<List<Long>>() {
        });
        if (deptList == null || deptList.size() < 1) {
            deptList = baseDao.findDeptIdByUserId(userId);
            redisUtils.set(RedisKeys.User.curdept(userId), deptList, DEPT_EXPIRE);
        }
        return deptList;
    }

    /**
     * 获取用户所有的部门，包含子部门,递归查询处理
     */
    @Override
    public List<Long> getUserAllDeptId(Long userId) {
        List<Long> allDeptList = redisUtils.get(RedisKeys.User.allDept(userId), new TypeToken<List<Long>>() {
        });
        if (allDeptList == null) {
            List<Long> parentIdList = getUserDeptId(userId);
            List<SysDeptEntity> list = sysDeptService.getAllDeptList();
            allDeptList = recursionDept(true, new CopyOnWriteArrayList<>(list), parentIdList);
            redisUtils.set(RedisKeys.User.allDept(userId), allDeptList, DEPT_EXPIRE);
        }
        return allDeptList;
    }

    /**
     * 获取用户所有的子部门,不包含用户所在部门递归查询处理
     */
    @Override
    public List<Long> getUserSubDeptId(Long userId) {
        List<Long> allDeptList = redisUtils.get(RedisKeys.User.subDept(userId), new TypeToken<List<Long>>() {
        });
        if (allDeptList == null) {
            List<Long> parentIdList = getUserDeptId(userId);
            List<SysDeptEntity> list = sysDeptService.getAllDeptList();
            allDeptList = recursionDept(false, new CopyOnWriteArrayList<>(list), parentIdList);
            redisUtils.set(RedisKeys.User.subDept(userId), allDeptList, DEPT_EXPIRE);
        }
        return allDeptList;
    }

    /**
     * new  by xieyuqing 2018年6月13日15:02:52
     *
     * @param userId 用户id
     * @return 返回用户所有部门id集合
     */
    @Override
    public List<Long> getUserDeptIdList(Long userId) {
        List<Long> allDeptList = redisUtils.get(RedisKeys.User.allDept(userId), new TypeToken<List<Long>>() {
        });
        if (allDeptList == null) {
            //系统管理员，拥有最高权限
            if (SystemId.SUPER_ADMIN.equals(ShiroUtils.getUserId())) {
                //返回系统所有部门id集合
                return sysDeptService.getAllDeptIdList();
            }
            //否则获取部门用户下的所有部门id集合
            List<Long> parentIdList = getUserDeptId(userId);
            List<SysDeptEntity> list = sysDeptService.findValidDeptList();
            allDeptList = recursionDept(true, new CopyOnWriteArrayList<>(list), parentIdList);
            redisUtils.set(RedisKeys.User.allDept(userId), allDeptList, DEPT_EXPIRE);
        }
        return allDeptList;
    }

    /**
     * 递归获取用户部门和子部门
     *
     * @param isRoot       是否显示根节点
     * @param list         需要遍历的列表
     * @param parentIdList 父节点编号列表
     */
    @Override
    public List<Long> recursionDept(boolean isRoot, List<SysDeptEntity> list, List<Long> parentIdList) {
        List<Long> allDeptList = new ArrayList<>();
        RecursionUtil.list(allDeptList, SysDeptEntity.class, "getDeptId", isRoot, new CopyOnWriteArrayList<>(list), parentIdList);
        return allDeptList;
    }

    /**
     * 根据用户ID，获取部门列表
     */
    @Override
    public List<SysDeptEntity> getDeptList(Long userId) {
        return baseDao.findDeptList(userId);
    }

    /**
     * 查询用户部门列表,包含部门信息
     */
    @Override
    public List<SysUserDeptEntity> getUserDeptInfoList(Long userId) {
        return baseDao.findUserDeptInfoList(userId);
    }

    /**
     * 根绝角色编号查询用户编号列表
     */
    @Override
    public List<Long> getUserIdByDeptId(Long deptId) {
        return baseDao.findUserIdByDeptId(deptId);
    }

    /**
     * 删除人员与部门关系
     */
    @Override
    public int deleteByDeptId(Long deptId) {
        return baseDao.deleteByDeptId(deptId);
    }

    @Override
    public List<UserDeptInfo> getUserDeptInfo(String deptName) {
        Long deptId = sysDeptService.getDeptByName(deptName);
        if (deptId == null) {
            log.error("部门：" + deptName + "不存在");
            return new ArrayList<>();
        }
        List<Long> allDeptUnderDeptId = sysDeptService.getAllDeptUnderDeptId(deptId);
        if (allDeptUnderDeptId.size() == 0) {
            log.error("部门编号：" + deptId + "不存在");
            return new ArrayList<>();
        }
        String deptIds = sysDeptService.deptIdListToString(allDeptUnderDeptId);
        return baseDao.getUserDeptInfo(deptIds);
    }


    /**
     * new  by gmm 2018年10月19日15:07:52
     * 获取用户所属部门
     */
    @Override
    public SysDeptEntity getUserDept(Long userId) {
        return baseDao.getUserDept(userId);
    }
}
