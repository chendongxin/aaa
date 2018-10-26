package com.hqjy.mustang.transfer.sms.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.common.model.crm.TransferCustomerInfo;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsReplyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_sms_reply 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/30 12:08ss
 */
@Mapper
public interface TransferSmsReplyDao extends BaseDao<TransferSmsReplyEntity, Long> {
    List<TransferSmsReplyEntity> findCustomerList(TransferCustomerInfo transferCustomerInfo);
    TransferSmsReplyEntity findCustomerOne(@Param(value = "customerId") Long customerId);
}