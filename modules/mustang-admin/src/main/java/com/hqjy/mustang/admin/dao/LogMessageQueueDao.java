package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.admin.model.entity.LogMessageQueueEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * log_message_queue 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/06 11:45
 */
@Mapper
public interface LogMessageQueueDao extends BaseDao<LogMessageQueueEntity, Long> {
}