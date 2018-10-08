package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.NcDealMsgDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDealEntity;

/**
 * @author xyq
 * @date create on 2018/9/30
 * @apiNote
 */
public interface TransferCustomerDealService extends BaseService<TransferCustomerDealEntity, Long> {

    /**
     * 处理NC成交业务
     *
     * @param ncDeal 消息
     * @return 返回处理结果
     * @author xyq
     * @date create on 2018/9/30
     */
    int processDealMsg(NcDealMsgDTO ncDeal);
}