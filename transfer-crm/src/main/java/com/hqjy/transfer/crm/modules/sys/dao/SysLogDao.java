package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_log 持久化层
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/05/22 14:50
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity, Long> {
}