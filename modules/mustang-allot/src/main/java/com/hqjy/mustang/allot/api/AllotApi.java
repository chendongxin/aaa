package com.hqjy.mustang.allot.api;

import com.hqjy.mustang.allot.service.impl.TransferAllotServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/5 14:56
 */
@RestController
@RequestMapping(Constant.API_PATH)
public class AllotApi {

    @Autowired
    private TransferAllotServiceImpl transferAllotService;

    /**
     * 招转重置部门分配算法
     */
    @GetMapping("/transfer/reset/dept/{deptId}")
    public void restDeptList(@PathVariable("deptId") Long deptId) {
        transferAllotService.restDeptList(deptId);
    }

    /**
     * 招转重置用户分配算法
     */
    @GetMapping("/transfer/reset/user/{deptId}")
    public void restUserList(@PathVariable("deptId") Long deptId) {
        transferAllotService.restUserList(deptId);
    }
}
