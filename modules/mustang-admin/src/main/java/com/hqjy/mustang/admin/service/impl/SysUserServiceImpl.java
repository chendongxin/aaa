package com.hqjy.mustang.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.admin.dao.SysUserDao;
import com.hqjy.mustang.admin.feign.AllotApiService;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserEntity;
import com.hqjy.mustang.admin.model.entity.SysUserRoleEntity;
import com.hqjy.mustang.admin.service.*;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.RegularUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用户管理
 *
 * @author : heshuangshuang
 * @date : 2018/1/19 15:31
 */
@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity, Long> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserDeptService sysUserDeptService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysCacheService sysCacheService;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private AllotApiService allotApiService;
    @Autowired
    private SysDeleteService sysDeleteService;
    /*@Autowired
    private SysScheduleService sysScheduleService;
    @Autowired
    private BizCustomerService bizCustomerService;*/

    /**
     * 查询用户基本信息
     */
    @Override
    public SysUserEntity findOneInfo(Long userId) {
        return super.findOne(userId);
    }

    @Override
    public SysUserEntity findOne(Long userId) {
        SysUserEntity user = baseDao.findOne(userId);
        if (user != null) {
            //获取用户所属的角色列表
            List<Long> roleIdList = sysUserRoleService.getRoleIdList(userId);
            user.setRoleIdList(roleIdList);
            //获取用户所有的部门列表(不包含子部门)
            List<Long> deptIdList = sysUserDeptService.getUserDeptId(userId);
            user.setDeptIdList(deptIdList);
            //获取用户部门关系
            List<SysUserDeptEntity> userDeptList = sysUserDeptService.getUserDeptInfoList(userId);
            user.setUserDeptList(userDeptList);
        }
        return user;
    }

    @Override
    public List<SysUserEntity> getUserByAllDeptId(Long deptId) {
        // 获取部门下所有子部门
        List<SysDeptEntity> list = new CopyOnWriteArrayList<>(sysDeptService.getAllDeptList());
        List<Long> allDeptList = sysUserDeptService.recursionDept(true, list,
                new ArrayList<>(Collections.singletonList(deptId)));
        return sysUserDao.findUserByDeptIdList(allDeptList);
    }

    /**
     * update xieyuqing 2018年6月2日18:05:59
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public List<SysUserEntity> getUserByDeptId(Long deptId) {
        if (null == deptId) {
            //系统管理员，拥有最高权限
            if (SystemId.SUPER_ADMIN.equals(ShiroUtils.getUserId())) {
                //获取所有用户列表
                return sysUserDao.findAllList();
            } else {
                List<Long> allDeptIdList = sysUserDeptService.getUserAllDeptId(ShiroUtils.getUserId());
                return sysUserDao.findUserByDeptIdList(allDeptIdList);
            }
        }
        return sysUserDao.getUserByDeptId(deptId);
    }

    /**
     * 分页查询用户信息
     */
    @Override
    public List<SysUserEntity> findPage(PageQuery pageQuery) {
        // 管理树点击的查询条件
        String treeValue = MapUtils.getString(pageQuery, "treeValue");
        if (StringUtils.isNotBlank(treeValue)) {
            // 获取部门下所有子部门,作为查询条件
            List<SysDeptEntity> list = new CopyOnWriteArrayList<>(sysDeptService.getAllDeptList());
            List<Long> allDeptList = sysUserDeptService.recursionDept(true, list,
                    new ArrayList<>(Collections.singletonList(Long.valueOf(treeValue))));
            pageQuery.put(MapUtils.getString(pageQuery, "treeKey"), allDeptList);
        }

        //数据过滤条件
        List<Long> allDeptIdList = sysUserDeptService.getUserAllDeptId(ShiroUtils.getUserId());
        pageQuery.put("allDeptId", allDeptIdList);

        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        List<SysUserEntity> list = baseDao.findPage(pageQuery);
        list.forEach(user -> {
            //查询用户角色
            user.setRoleList(sysUserRoleService.getRoleList(user.getUserId()));
            //查询用户部门
            //user.setDeptList(sysUserDeptService.getDeptList(user.getUserId()));
            user.setUserDeptList(sysUserDeptService.getUserDeptInfoList(user.getUserId()));
            //user.setAvatar(Optional.ofNullable(user.getAvatar()).map(s -> OSSFactory.ali().generatePresignedUrl(s, 86400)).orElse(null));
        });
        return list;
    }

    /**
     * 保存用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysUserEntity user) {
        // 越权检测,只能保存自己部门以下的子部门的用户
        List<SysUserDeptEntity> deptList = user.getUserDeptList();
        deptList.forEach(dept -> {
            boolean check = sysDeptService.checkPermission(true, dept.getDeptId());
            if (!check) {
                throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
            }
        });
        user.setCreateId(ShiroUtils.getUserId());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        int result = baseDao.save(user);
        if (result > 0) {
            //保存用户与角色关系
            sysUserRoleService.save(user.getUserId(), user.getRoleIdList());
            //保存用户与部门关系
            sysUserDeptService.save(user.getUserId(), user.getUserDeptList());
        }
        return result;
    }

    /**
     * 修改用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserEntity user) {
        //非超级管理员不能修改自己
        /*if (!SystemId.SUPER_ADMIN.equals(getUserId()) && getUserId().equals(user.getUserId())) {
            throw new RRException(StatusCode.PERMISSION_ONESELF);
        }*/

        //用户原来信息
        SysUserEntity oldUser = baseDao.findOne(user.getUserId());

        // 查询用户原来的部门列表
        List<SysUserDeptEntity> oldUserDeptList = sysUserDeptService.getUserDeptList(user.getUserId());
        List<SysUserDeptEntity> newUserDeptList = user.getUserDeptList();

        Set<Long> changeWeightDeptIdset = new HashSet<>();
        // 检测是否修改了权重
        for (SysUserDeptEntity dept : newUserDeptList) {
            Integer weights = dept.getWeights();
            Long deptId = dept.getDeptId();
            for (SysUserDeptEntity userDept : oldUserDeptList) {
                if (userDept.getDeptId().equals(deptId) && !userDept.getWeights().equals(weights)) {
                    // 不允许修改其他部门的权重
                    if (!sysDeptService.checkPermission(true, deptId)) {
                        throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
                    }
                    changeWeightDeptIdset.add(deptId);
                }
            }
        }
      /*  // 查询用户原来的角色列表
        List<Long> oldUserRoleIdList = sysUserRoleService.getRoleIdList(user.getUserId());
        List<Long> newUserRoleList = user.getRoleIdList();
        //如果修改了角色
        if (isChangeRole(oldUserRoleIdList, newUserRoleList)) {
            //旧角色检测越权，不能修改拥有其他非操作用户的角色的用户
            for (Long oldRole : oldUserRoleIdList) {
                if (!sysRoleDeptService.checkRole(oldRole)) {
                    throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
                }
            }
            // 新角色检测越权
            for (Long userRole : newUserRoleList) {
                if (!sysRoleDeptService.checkRole(userRole)) {
                    throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
                }
            }
        }*/

        boolean isChangePassword = false;
        //密码不为空，则进行修改
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            // 验证密码格式
            RegularUtils.checkPassWord(user.getPassword());
            String salt = oldUser.getSalt();
            user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
            isChangePassword = true;
        }

        user.setUpdateId(ShiroUtils.getUserId());
        int result = baseDao.update(user);
        if (result > 0) {
            //保存用户与角色关系
            sysUserRoleService.save(user.getUserId(), user.getRoleIdList());
            //保存用户与部门关系
            sysUserDeptService.save(user.getUserId(), user.getUserDeptList());
            // 清空这个用户的授权缓存
            sysCacheService.cleanUserAuz(user.getUserId());
            //修改了密码
            if (isChangePassword) {
                //清空用户认证缓存
                sysCacheService.cleanUserAut(user.getUserId());
            }
        }
        changeWeightDeptIdset.forEach(deptId -> {
            // 修改了权重部门下人员需要刷新分配算法
            allotApiService.restUserList(deptId);
        });
        return result;
    }

    /**
     * 用户修改部门
     */
    @Override
    public int updateUserDept(SysUserEntity user) {

        //非超级管理员不能修改自己
        if (!SystemId.SUPER_ADMIN.equals(ShiroUtils.getUserId()) && ShiroUtils.getUserId().equals(user.getUserId())) {
            throw new RRException(StatusCode.PERMISSION_ONESELF);
        }

        List<SysUserDeptEntity> newUserDeptList = user.getUserDeptList();
        newUserDeptList.forEach(dept -> {
            boolean check = sysDeptService.checkPermission(true, dept.getDeptId());
            if (!check) {
                throw new RRException(StatusCode.PERMISSION_UNAUTHORIZED);
            }
        });

        // 查询用户原来的部门列表
        List<SysUserDeptEntity> oldUserDeptList = sysUserDeptService.getUserDeptList(user.getUserId());

        //提取出操作用的可支配的部门
        List<SysUserDeptEntity> noUserDeptList = new ArrayList<>();
        oldUserDeptList.forEach(userDept -> {
            if (!sysDeptService.checkPermission(true, userDept.getDeptId())) {
                noUserDeptList.add(userDept);
            }
        });
        //保存用户与部门关系,不删除其他部门信息
        int count = sysUserDeptService.save(user.getUserId(), newUserDeptList, noUserDeptList);
        if (count > 0) {
            // 合并新旧部门
            newUserDeptList.addAll(oldUserDeptList);
            Set<Long> deptIdSet = new HashSet<>();
            // 新部门检测越权
            for (SysUserDeptEntity userDept : newUserDeptList) {
                deptIdSet.add(userDept.getDeptId());
            }
            deptIdSet.forEach(deptId -> {
                // 新的部门和旧的部门都需要刷新分配算法
                allotApiService.restUserList(deptId);
            });
            // 清空这个用户的授权缓存
            sysCacheService.cleanUserAuz(user.getUserId());
        }
        // 删除这个用户在原来部门中的的排班信息
        // sysScheduleService.deleteUserOldDeptIdSchedule(user.getUserId());
        // 转移用户在原部门下持有的非成交商机到公海
        //bizCustomerService.updateUserBizToPublic(user.getUserId(), true);

        return count;
    }

    /**
     * 检查是否修改了部门
     */
    private boolean isChangeDept(List<SysUserDeptEntity> oldUserDeptList, List<SysUserDeptEntity> userDept) {
        int oldSize = oldUserDeptList.size();
        int newSize = userDept.size();
        //部门数量不等，一定修改了部门
        if (oldSize != newSize) {
            return true;
        }
        int count = 0;
        for (SysUserDeptEntity oldDept : oldUserDeptList) {
            for (SysUserDeptEntity newDept : userDept) {
                if (newDept.getDeptId().equals(oldDept.getDeptId())) {
                    count++;
                }
            }
        }
        // 如果新旧部门匹配的数量和原来部门数量不一致，一定修改了部门
        return oldSize != count;
    }

    /**
     * 检查是否修改了角色
     */
    private boolean isChangeRole(List<Long> oldUserRoleList, List<Long> userRole) {
        int oldSize = oldUserRoleList.size();
        int newSize = userRole.size();
        //角色数量不等，一定修改了角色
        if (oldSize != newSize) {
            return true;
        }
        int count = 0;
        for (Long oldRoleId : oldUserRoleList) {
            for (Long newRoleId : userRole) {
                if (oldRoleId.equals(newRoleId)) {
                    count++;
                }
            }
        }
        // 如果新旧角色匹配的数量和原来角色数量不一致，一定修改了角色
        return oldSize != count;
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long userId) {
        if (SystemId.SUPER_ADMIN.equals(userId)) {
            throw new RRException("系统管理员不能删除");
        }
        if (ShiroUtils.getUserId().equals(userId)) {
            throw new RRException(StatusCode.PERMISSION_ONESELF);
        }
        SysUserEntity userInfo = baseDao.findOne(userId);
        int count = super.delete(userId);
        if (count > 0) {
            String keyWord = StringUtils.UUID();
            //记录用户删除日志
            log.debug("记录用户删除日志");
            sysDeleteService.saveLog(keyWord, SysDeptEntity.class.getSimpleName(), userInfo, "删除用户");
            //记录用户部门删除日志
            log.debug("记录用户部门删除日志");
            List<SysUserDeptEntity> userDeptList = sysUserDeptService.getUserDeptList(userId);
            if (userDeptList.size() > 0) {
                sysDeleteService.saveLogs(keyWord, SysUserDeptEntity.class.getSimpleName(), userDeptList, "删除用户部门关系");
            }
            //记录用户角色删除日志
            log.debug("记录用户角色删除日志");
            SysUserRoleEntity userRoleQuery = new SysUserRoleEntity();
            userRoleQuery.setUserId(userId);
            List<SysUserRoleEntity> userRoleList = sysUserRoleService.findList(userRoleQuery);
            if (userRoleList.size() > 0) {
                sysDeleteService.saveLogs(keyWord, SysUserRoleEntity.class.getSimpleName(), userRoleList, "删除用户角色关系");
            }
            // 非成交商机放入公海
            //bizCustomerService.updateUserBizToPublic(userId, false);

            // 删除所有排班
            // sysScheduleService.deleteUserAllSchedule(userId);

            //删除用户与部门关系
            sysUserDeptService.deleteByUserId(userId);
            //删除用户与角色关系
            sysUserRoleService.deleteByUserId(userId);
            //清空用户授权缓存
            sysCacheService.cleanUserAuz(userId);
            //清空用户认证缓存
            sysCacheService.cleanUserAut(userId);
            // 刷新分配算法
            List<Long> deptIdList = sysUserDeptService.getUserDeptId(userId);
            deptIdList.forEach(deptId -> {
                allotApiService.restUserList(deptId);
            });
        }
        return count;
    }

    /**
     * 修改密码
     */
    @Override
    public int updatePassword(HashMap<String, Object> param) {
        String oldPassword = MapUtils.getString(param, "oldPass");
        String newPassword = MapUtils.getString(param, "newPass");
        if (StringUtils.isEmpty(oldPassword)) {
            throw new RRException("旧密码不能为空");
        }
        if (StringUtils.isEmpty(newPassword)) {
            throw new RRException("新密码不能为空");
        }
        // 验证密码格式
        RegularUtils.checkPassWord(newPassword);

        SysUserEntity sysUserOld = baseDao.findOne(ShiroUtils.getUserId());
        //旧密码错误
        if (!sysUserOld.getPassword().equals(new Sha256Hash(oldPassword, sysUserOld.getSalt()).toHex())) {
            throw new RRException("原密码错误");
        }
        //新密码与原密码
        if (oldPassword.equals(newPassword)) {
            throw new RRException("密码未修改");
        }
        //用户盐,随机数
        String salt = RandomStringUtils.randomAlphanumeric(20);
        SysUserEntity newUser = new SysUserEntity();
        newUser.setUserId(sysUserOld.getUserId());
        newUser.setSalt(salt);
        //加密密码
        newUser.setPassword(new Sha256Hash(newPassword, salt).toHex());
        newUser.setUpdateId(ShiroUtils.getUserId());
        return baseDao.update(newUser);
    }

    /**
     * 修改个人信息
     */
    @Override
    public int updateUserInfo(SysUserEntity sysUser) {
        //用户原来信息
        SysUserEntity sysUserOld = baseDao.findOne(ShiroUtils.getUserId());
        //修改标志
        boolean flag = Boolean.FALSE;
        //新的PO
        SysUserEntity newUser = new SysUserEntity();
        newUser.setUserId(sysUser.getUserId());
        String userName = sysUser.getUserName();
        String phone = sysUser.getPhone();
        //用户名是否修改
        if (!userName.equals(sysUserOld.getUserName())) {
            newUser.setUserName(userName);
            flag = true;
        }
        //手机是否修改
        if (!phone.equals(sysUserOld.getPhone())) {
            newUser.setPhone(phone);
            flag = true;
        }
        if (flag) {
            newUser.setUpdateId(ShiroUtils.getUserId());
            return baseDao.update(newUser);
        }
        return 0;
    }

    /**
     * 修改头像
     */
    @Override
    public int updateUserAvatar(MultipartFile file) {
        if (!file.isEmpty()) {
            String oldKey = Optional.ofNullable(baseDao.findOne(ShiroUtils.getUserId())).map(SysUserEntity::getAvatar).orElse(null);
            //aliyun目录
            String key = "avatar/" + ShiroUtils.getUserId() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".png";
            // 上传头像
            //OSSFactory.ali().uploadOnly(file.getInputStream(), key);
            SysUserEntity sysUser = new SysUserEntity();
            sysUser.setUserId(ShiroUtils.getUserId());
            sysUser.setAvatar(key);
            sysUser.setUpdateId(ShiroUtils.getUserId());
            int count = baseDao.update(sysUser);
            if (count > 0) {
                updateUserRedisInfo();
                // 删除原来头像
                // OSSFactory.ali().delete(oldKey);
            }
            return count;
        }
        return 0;
    }

    @Override
    public List<SysUserEntity> listAll() {
        return baseDao.findAllList();
    }

    /**
     * 更新用户redis信息
     */
    @Override
    public void updateUserRedisInfo() {
        LoginUserDTO oldUser = redisUtils.get(RedisKeys.User.token(ShiroUtils.getUserId()), LoginUserDTO.class);
        LoginUserDTO userDTO = shiroService.findByUserId(ShiroUtils.getUserId());
        userDTO.setToken(oldUser.getToken());
        userDTO.setLoginIp(oldUser.getLoginIp());
        userDTO.setLoginTime(oldUser.getLoginTime());
        // userDTO.setAvatar(Optional.ofNullable(userDTO.getAvatar()).map(s -> OSSFactory.ali().generatePresignedUrl(s, 86400)).orElse(null));
        //token写入缓存
        redisUtils.set(RedisKeys.User.token(userDTO.getUserId()), userDTO, -1);
    }


    @Override
    public String userIdListToIdString(List<Long> userEntityList) {
        List<String> userIdList = new ArrayList<>();
        userEntityList.forEach(u -> {
            userIdList.add(String.valueOf(u));
        });
        return StringUtils.listToString(userIdList);
    }
}
