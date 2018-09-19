package com.hqjy.mustang.transfer.crawler.service.impl;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.crawler.dao.TransferEmailDao;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;
import com.hqjy.mustang.transfer.crawler.service.TrasferEmailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:17
 */
@Service
public class TrasferEmailServiceImpl extends BaseServiceImpl<TransferEmailDao, TransferEmailEntity, Long> implements TrasferEmailService {

    /**
     * 查询所有有效email配置
     */
    @Override
    public List<TransferEmailEntity> findAllValid() {
        return baseDao.findAllValid();
    }

    /**
     * 修改简历发送状态
     */
    @Override
    public int updateStatus(Long id, int status) {
        return baseDao.updateStatus(id, status);
    }

    @Override
    public int save(TransferEmailEntity entity) {
        entity.setCreateUserId(ShiroUtils.getUserId());
        entity.setCreateUserName(ShiroUtils.getUserName());
        return super.save(entity);
    }

    @Override
    public int update(TransferEmailEntity entity) {
        entity.setUpdateUserId(ShiroUtils.getUserId());
        entity.setUpdateUserName(ShiroUtils.getUserName());
        return super.update(entity);
    }
}
