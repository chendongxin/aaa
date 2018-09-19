package com.hqjy.mustang.allot.service;

import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.service.impl.WeightRoundAreaImpl;
import com.hqjy.mustang.allot.service.impl.WeightRoundDeptImpl;
import com.hqjy.mustang.allot.service.impl.WeightRoundUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/14 14:20
 */
public interface AbstractAllotService<T extends Serializable> {

    /**
     * 咨询流程分配
     */
    void allot(ContactSaveResultDTO saveResultDTO, T customer);

    /**
     * 重置List
     */
    void listRest(boolean onlyDept, Long deptId);

}
