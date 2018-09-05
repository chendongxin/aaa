package com.hqjy.transfer.allot.service;

import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;

/**
 * 商机消息分配业务
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 15:10
 */
public interface BizHandleService {

    /**
     * 商机消息分配逻辑
     */
    Boolean process(String msgId, AllotCustomerEntity content);
}
