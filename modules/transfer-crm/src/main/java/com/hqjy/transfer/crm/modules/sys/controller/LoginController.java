package com.hqjy.transfer.crm.modules.sys.controller;

import com.hqjy.transfer.common.base.annotation.SysLog;
import com.hqjy.transfer.common.base.base.AbstractController;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.common.redis.utils.RedisKeys;
import com.hqjy.transfer.common.redis.utils.RedisUtils;
import com.hqjy.transfer.crm.modules.sys.model.dto.LoginUserDTO;
import com.hqjy.transfer.crm.modules.sys.service.LoginService;
import com.hqjy.transfer.crm.modules.sys.service.ShiroService;
import com.hqjy.transfer.crm.modules.sys.service.SysMenuService;
import com.hqjy.transfer.crm.common.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登录控制器
 *
 * @author : heshuangshuang
 * @date : 2018/3/22 14:39
 */
@Api(tags = "登录接口", description = "LoginController")
@RestController
@RequestMapping("/")
public class LoginController extends AbstractController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录
     */
    @ApiOperation(value = "用户登录", notes = "参数：[account 企业账户]，[phone 电话]，[password 密码]" +
            "\n示例： {\"account\":\"hqjy\",\"phone\":\"17600222250\",\"password\":\"admin\"}")
    @ApiImplicitParam(paramType = "body", name = "userDTO", value = "登录用户信息", required = true, dataType = "LoginUserDTO")
    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestBody LoginUserDTO userDTO) {
        //验证用户登录
        LoginUserDTO user = loginService.getUserByPhone(userDTO.getPhone(), userDTO.getPassword());
        return R.ok(loginService.doLogin(user));
    }

    /**
     * 获取登录用户的信息
     */
    @ApiOperation(value = "获取登录用户的信息", notes = "获取当前用户登录信息，与登录时返回数据一致")
    @GetMapping("/userInfo")
    public R info() {
        LoginUserDTO user = redisUtils.get(RedisKeys.User.token(ShiroUtils.getUserId()), LoginUserDTO.class);
        // 查询用户部门和角色列表和名称
        return R.ok(loginService.processDeptRole(user));
    }

    /**
     * 获得用户菜单
     */
    @GetMapping(value = "/userMenu")
    @ApiOperation(value = "获得用户菜单", notes = "获取当前用户所拥有的[菜单]数据")
    public Map<String, Object> userMenu() {
        return R.ok(sysMenuService.getUserMenuTree(ShiroUtils.getUserId()));
    }

    /**
     * 获得用户菜单权限
     */
    @GetMapping(value = "/userMenuName")
    @ApiOperation(value = "获得用户菜单权限", notes = "获取当前用户所拥有的[菜单权限]数据")
    public Map<String, Object> userMenuName() {
        return R.ok(sysMenuService.getUserMenuNameList(ShiroUtils.getUserId()));
    }

    /**
     * 获得用户权限
     */
    @GetMapping(value = "/userPerm")
    @ApiOperation(value = "获得用户权限", notes = "获取当前用户所拥有的[权限]数据")
    public Map<String, Object> userPerm() {
        return R.ok(shiroService.getUserPermissions(ShiroUtils.getUserId()));
    }

    /**
     * 解锁屏幕，验证密码
     */
    @ApiOperation(value = "解锁屏幕，验证密码", notes = "参数：[password 密码] \n示例：{\"password\":\"123456\"}")
    @PostMapping("/unlock")
    @SysLog("解锁屏幕")
    public R lockScreen(@RequestBody LoginUserDTO loginUser) {
        if (ShiroUtils.getUser().getPassword().equals(new Sha256Hash(loginUser.getPassword(), ShiroUtils.getUser().getSalt()).toHex())) {
            return R.ok();
        }
        return R.error("密码错误");
    }

    /**
     * 退出
     */
    @ApiOperation(value = "登出系统", notes = "用户退出系统，清除用户在线状态和相关资源")
    @GetMapping(value = "/logout")
    @SysLog("用户登出")
    public R logout() {
        redisUtils.delete(RedisKeys.User.token(ShiroUtils.getUserId()));
        redisUtils.cleanKeys(RedisKeys.User.perm(ShiroUtils.getUserId()));
        return R.ok();
    }

}
