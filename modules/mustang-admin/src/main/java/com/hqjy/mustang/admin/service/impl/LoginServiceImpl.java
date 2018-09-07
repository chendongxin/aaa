package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.service.*;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.common.web.utils.HttpContextUtils;
import com.hqjy.mustang.common.web.utils.IPUtils;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/5/23 9:32
 */
@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserDeptService sysUserDeptService;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 根据用户和密码获取用户信息
     */
    @Override
    public LoginUserDTO getUserByPhone(String phone, String password) {
        //用户信息
        LoginUserDTO user = shiroService.findByPhone(phone);
        //账号不存在
        if (user == null) {
            throw new RRException(StatusCode.LOGIN_USER_NOEXIST);
        }
        //密码错误
        if (!user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            throw new RRException(StatusCode.LOGIN_PASSWORD_ERROR);
        }
        //账号锁定
        if (Constant.Status.DISABLE.equals(user.getStatus())) {
            throw new RRException(StatusCode.LOGIN_USER_LOCK);
        }
        return user;
    }

    /**
     * 根据用户Id获取用户信息
     */
    @Override
    public LoginUserDTO getUserById(Long uesrId) {
        //用户信息
        LoginUserDTO user = shiroService.findByUserId(uesrId);
        //账号不存在
        if (user == null) {
            throw new RRException(StatusCode.LOGIN_USER_NOEXIST);
        }
        //账号锁定
        if (Constant.Status.DISABLE.equals(user.getStatus())) {
            throw new RRException(StatusCode.LOGIN_USER_LOCK);
        }
        return user;
    }

    /**
     * 登录成功回调
     */
    @Override
    public LoginUserDTO doLogin(LoginUserDTO user) {
        //签发用户token
        String token = TokenUtils.createToken(user.getUserId(), user.getUserName(), Constant.JWT_SIGN_KEY);
        user.setToken(token);
        user.setJti(TokenUtils.tokenInfo(token, Constant.JWT_ID, String.class));
        // user.setAvatar(Optional.ofNullable(user.getAvatar()).map(s -> OSSFactory.ali().generatePresignedUrl(s, 86400)).orElse(null));
        //用户信息放入redis中
        redisUtils.set(RedisKeys.User.token(user.getUserId()), user, TokenUtils.getProlong());
        //清除用户授权缓存
        redisUtils.delete(RedisKeys.User.perm(user.getUserId()));
        //更新本次登录IP信息
        shiroService.updateUserLoginInfo(user, IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        //保存登录日志
        sysLogService.save(user.getUserName(), "用户登录", getClass().getCanonicalName(), user);
        return user;
    }

    /**
     * 查询用户部门和角色
     */
    @Override
    public LoginUserDTO processDeptRole(LoginUserDTO userDTO) {
        userDTO.setRoleList(sysUserRoleService.getRoleList(userDTO.getUserId()));
        userDTO.setDeptList(sysUserDeptService.getDeptList(userDTO.getUserId()));
        return userDTO;
    }

}
