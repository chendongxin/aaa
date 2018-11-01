package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserDeptEntity;
import com.hqjy.mustang.admin.service.SysUserDeptService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.admin.service.SysDeptService;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:31
 */
@RestController
@RequestMapping(Constant.API_PATH + "/dept")
public class SysDeptApi {


    private SysDeptService sysDeptService;
    private SysUserDeptService sysUserDeptService;

    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @Autowired
    public void setSysUserDeptService(SysUserDeptService sysUserDeptService) {
        this.sysUserDeptService = sysUserDeptService;
    }

    /**
     * 根据部门Id查询
     */
    @GetMapping("/{deptId}")
    public SysDeptInfo findByDeptId(@PathVariable("deptId") Long deptId) {
        return PojoConvertUtil.convert(sysDeptService.findOne(deptId), SysDeptInfo.class);
    }



    /**
     * @author : gmm
     * @date : 2018/9/15 16:31
     * 获取所选部门的旗下部门id
     */
    @GetMapping(value = "/all/{deptId}")
    public List<Long> getAllDeptId(@PathVariable("deptId") Long deptId) {
        return sysDeptService.getAllDeptUnderDeptId(deptId);
    }

    /**
     * @author : gmm
     * @date : 2018/9/15 16:31
     * 查询用户对应的部门列表
     */
    @GetMapping(value = "/entity/userId")
    public List<SysUserDeptEntity> getDeptByCustomerId(Long userId) {
        return sysUserDeptService.getUserDeptList(userId);
    }

    /**
     * @author : gmm
     * @date : 2018/9/15 16:31
     * 获取用户对应的部门列表ID
     */
    @GetMapping(value = "/long/userId")
    public List<Long> getUserDeptIdList(Long userId) {
        return sysUserDeptService.getUserDeptIdList(userId);
    }

    /**
     * @author : gmm
     * @date : 2018/9/15 16:31, update xyq 2018年10月8日16:22:32
     * 获取用户对应的部门列表
     */
    @ApiOperation(value = "返回用户所属部门列表", notes = "返回用户所属部门列表")
    @GetMapping(value = "/getUserDeptList/{userId}")
    public List<SysDeptEntity> getUserDeptList(@PathVariable("userId") Long userId) {
        return sysDeptService.getUserDeptList(userId);
    }

    /**
     * @author : gmm
     * @date : 2018/10/15
     * 获取用户所属部门
     */
    @ApiOperation(value = "返回用户所属部门", notes = "返回用户所属部门")
    @GetMapping(value = "/userDept/{userId}")
    public SysDeptEntity getUserDept(@PathVariable("userId") Long userId) {
        return sysUserDeptService.getUserDept(userId);
    }

    /**
     * 根据部门名称获取部门Id
     *
     * @author : xyq
     * @date : 2018年10月12日09:17:21
     */
    @GetMapping(value = "/getDeptByName")
    public Long getDeptByName(@RequestParam("deptName") String deptName) {
        return sysDeptService.getDeptByName(deptName);
    }

    /**
     * 获取所选部门的旗下部门（包括所选部门）
     *
     * @param deptId 部门ID
     * @return 返回部门集合
     * @author ADD xyq 2018年10月12日09:29:30
     */
    @GetMapping(value = "/getAllDeptUnderDeptId")
    public List<Long> getAllDeptUnderDeptId(@RequestParam("deptId") Long deptId) {
        return sysDeptService.getAllDeptUnderDeptId(deptId);
    }

    /**
     * 根据部门id字符串获取部门信息
     *
     * @param deptIdList 部门ID集合字符串（'1','2','3'）
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:44:08
     */
    @GetMapping(value = "/getDeptEntityByDeptIds")
    public List<SysDeptEntity> getDeptEntityByDeptIds(@RequestParam("deptIdList") String deptIdList) {
        return sysDeptService.getDeptEntityByDeptIds(deptIdList);
    }

    /**
     * 根据部门名称取该部门旗下部门信息（包括所选部门）
     *
     * @param deptName 部门名称
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:55:50
     */
    @GetMapping(value = "/getDeptEntityByDeptName")
    public List<SysDeptEntity> getDeptEntityByDeptName(@RequestParam("deptName") String deptName) {
        return sysDeptService.getDeptEntityByDeptName(deptName);
    }


    /**
     * 根据部门Id取该部门旗下部门信息（包括所选部门）
     *
     * @param deptId 部门Id
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:55:50
     */
    @GetMapping(value = "/getDeptEntityByDeptId")
    public List<SysDeptEntity> getDeptEntityByDeptId(@RequestParam("deptId") Long deptId) {
        return sysDeptService.getDeptEntityByDeptId(deptId);
    }

}

