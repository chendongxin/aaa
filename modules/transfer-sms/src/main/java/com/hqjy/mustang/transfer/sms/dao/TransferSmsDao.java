package com.hqjy.mustang.transfer.sms.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_sms 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:54
 */
@Mapper
public interface TransferSmsDao extends BaseDao<TransferSmsEntity, Long> {
}