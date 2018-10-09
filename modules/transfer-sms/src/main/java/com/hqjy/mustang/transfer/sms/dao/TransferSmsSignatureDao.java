package com.hqjy.mustang.transfer.sms.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_sms_signature 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:14
 */
@Mapper
public interface TransferSmsSignatureDao extends BaseDao<TransferSmsSignatureEntity, Long> {

    /**
     * 根据部门编号获取签名列表
     */
    List<TransferSmsSignatureEntity> getListByDeptId(Long deptId);
}