package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.SysDeptService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取所有部门
     */
    @ApiOperation(value = "获取所有部门信息", notes = "获取所有部门信息")
    @GetMapping(value = "/dept/all")
    public R getAllDept() {
        return R.ok(sysDeptService.getAllDeptList());
    }

    /**
     * 获取所有部门ID
     */
    @ApiOperation(value = "获取所选部门的旗下部门", notes = "获取所选部门的旗下部门")
    @GetMapping(value = "/dept/all/id")
    public List<Long> getAllDeptId(Long deptId) {
        return sysDeptService.getAllDeptUnderDeptId(deptId);
    }

    /**
     * 根据部门Id查询
     */
    @GetMapping("/{deptId}")
    public SysDeptInfo findByDeptId(@PathVariable("deptId") Long deptId) {
        return PojoConvertUtil.convert(sysDeptService.findOne(deptId), SysDeptInfo.class);
    }

}
