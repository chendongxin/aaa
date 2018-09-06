package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.admin.model.entity.SysDeleteLogEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
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