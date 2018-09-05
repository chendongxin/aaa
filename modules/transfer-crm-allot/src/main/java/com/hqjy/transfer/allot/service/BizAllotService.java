package com.hqjy.transfer.allot.service;

import com.hqjy.transfer.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;

/**
 * @author : heshuangshuang
 * @date : 2018/6/15 9:35
 */
public interface BizAllotService {

    /**
     * 首次咨询流程分配
     */
    Long firstBizAllot(ContactSaveResultDTO saveResultDTO, AllotCustomerEntity customer);

    /**
     * 二次咨询流程分配
     */
    Long repeatBizAllot(ContactSaveResultDTO saveResultDTO, AllotCustomerEntity customer);

    /**
     * 重置List
     */
    void listRest(boolean onlyDept, Long deptId);

}
