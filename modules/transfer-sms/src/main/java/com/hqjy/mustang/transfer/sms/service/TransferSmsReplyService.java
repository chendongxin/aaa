package com.hqjy.mustang.transfer.sms.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsReplyEntity;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 11:36
 */
public interface TransferSmsReplyService extends BaseService<TransferSmsReplyEntity, Long> {
    /**
     * 回复短信
     */
    int smsReply(Long id, TransferSmsEntity smsEntity);

    /**
     * 获取短信回复信息
     */
    List<TransferSmsEntity> replyList(Long id, PageQuery pageQuery);
}
