package com.hqjy.mustang.admin.feign.impl;

import com.hqjy.mustang.admin.feign.AllotApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class AllotApiServiceFallbackImpl implements AllotApiService {

    /**
     * 招转重置部门分配算法
     */
    @Override
    public void restDeptList(Long deptId) {
        log.error("调用{}异常:{},deptId：{}", "招转重置部门分配算法", deptId);
    }

    /**
     * 招转重置分配算法
     */
    @Override
    public void restUserList(Long deptId) {
        log.error("调用{}异常:{},deptId：{}", "招转重置用户分配算法", deptId);
    }
}
