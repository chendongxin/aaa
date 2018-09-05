package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDeleteLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_delete_log 持久化层
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/03/23 16:41
 */
@Mapper
public interface SysDeleteLogDao extends BaseDao<SysDeleteLogEntity, Long> {
}