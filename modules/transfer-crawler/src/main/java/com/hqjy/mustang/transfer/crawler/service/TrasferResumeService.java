package com.hqjy.mustang.transfer.crawler.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;

import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.Date;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:17
 */
public interface TrasferResumeService extends BaseService<TransferResumeEntity, Long> {

    boolean start(Date beforeDate);

    /**
     * 查询数据库中最后一条记录
     */
    TransferResumeEntity findLast();
}
