package com.hqjy.mustang.transfer.sms.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_sms_template 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:15
 */
@Mapper
public interface TransferSmsTemplateDao extends BaseDao<TransferSmsTemplateEntity, Long> {

    /**
     * 根据部门编号获取模版列表
     */
    List<TransferSmsSignatureEntity> getListByDeptId(Long deptId);
}