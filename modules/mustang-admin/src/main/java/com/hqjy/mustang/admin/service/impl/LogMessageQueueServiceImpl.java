package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.LogMessageQueueDao;
import com.hqjy.mustang.admin.model.entity.LogMessageQueueEntity;
import com.hqjy.mustang.admin.service.LogMessageQueueService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 消息队列接受消息日志记录
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 11:20
 */
@Service
public class LogMessageQueueServiceImpl extends BaseServiceImpl<LogMessageQueueDao, LogMessageQueueEntity, Long> implements LogMessageQueueService {

}
