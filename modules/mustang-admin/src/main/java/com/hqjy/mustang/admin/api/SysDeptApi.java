package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
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

    @Autowired
    private SysDeptService sysDeptService;

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
    @ApiOperation(value = "获取所选部门的旗下部门", notes = "获取所选部门的旗下部门")
    @GetMapping(value = "/all/{deptId}")
    public List<Long> getAllDeptId(@PathVariable("deptId") Long deptId) {
        return sysDeptService.getAllDeptUnderDeptId(deptId);
    }

    /**
     * @author : gmm
     * @date : 2018/9/15 16:31, update xyq 2018年10月8日16:22:32
     * 获取用户对应的部门列表
     */
    @ApiOperation(value = "查询用户对应的部门", notes = "查询用户对应的部门")
    @GetMapping(value = "/getUserDeptList")
    public List<SysDeptEntity> getUserDeptList(@RequestParam("userId") Long userId) {
        return sysDeptService.getUserDeptList(userId);
    }
}

