package com.hqjy.mustang.allot.feign.impl;

import com.hqjy.mustang.allot.feign.SysDeptApiService;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class SysDeptApiServiceFallbackImpl implements SysDeptApiService {

    /**
     * 根据部门Id查询部门
     */
    @Override
    public SysDeptInfo findOne(@PathVariable("deptId") Long deptId) {
        log.error("调用{}异常:{},deptId：{}", "根据部门Id查询部门", deptId);
        return null;
    }

}
