package com.hqjy.mustang.allot.service;

import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/14 14:20
 */
public interface AbstractAllotService<T extends Serializable> {

    /**
     * 咨询流程分配
     */
    TransferAllotCustomerEntity allot(ContactSaveResultDTO saveResultDTO, T customer);

    /**
     * 重置List
     */
    void restUserList(Long deptId);

    /**
     * 重置List
     */
    void restDeptList(Long deptId);
}
