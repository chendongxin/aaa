package com.hqjy.mustang.transfer.crawler.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:17
 */
public interface TrasferEmailService extends BaseService<TransferEmailEntity, Long> {

    /**
     * 查询所有有效email配置
     */
    List<TransferEmailEntity> findAllValid();

    /**
     * 修改简历发送状态
     */
    int updateStatus(Long id, int status);
}
