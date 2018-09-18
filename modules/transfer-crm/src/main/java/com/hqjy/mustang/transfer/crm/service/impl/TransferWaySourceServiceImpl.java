package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.dao.TransferWaySourceDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferWaySourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferWaySourceServiceImpl extends BaseServiceImpl<TransferWaySourceDao, TransferWaySourceEntity, Long> implements TransferWaySourceService {

    @Autowired
    private TransferWaySourceDao transferWaySourceDao;
    @Autowired
    private TransferGenWayDao transferGenWayDao;

    /**
     * 删除指定平台下的推广方式
     */
    @Override
    public int deleteBatch(Long[] ids) {
        return baseDao.deleteBatch(ids);
    }

    /**
     * 保存推广平台下的推广方式
     */
    @Override
    public int saveWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO) {
        TransferWaySourceEntity transferWaySourceEntity = new TransferWaySourceEntity();
        if (transferGenWayDao.findOneByGenName(transferGenWaySourceDTO.getWayName()) == null) {
            TransferGenWayEntity transferGenWayEntity = new TransferGenWayEntity();
            transferGenWayEntity.setGenWay(transferGenWaySourceDTO.getWayName());
            transferGenWayEntity.setStatus((byte)0);
            transferGenWayEntity.setCreateUserId(getUserId());
            transferGenWayEntity.setCreateUserName(getUserName());
            int count = transferGenWayDao.save(transferGenWayEntity);
            if (count > 0) {
                transferWaySourceEntity.setWayId(transferGenWayEntity.getWayId());
            } else {
                throw new RRException(StatusCode.DATABASE_SAVE_FAILURE);
            }
        } else {
            TransferGenWayEntity transferGenWayEntity = transferGenWayDao.findOneByGenName(transferGenWaySourceDTO.getWayName());
            transferWaySourceEntity.setWayId(transferGenWayEntity.getWayId());
        }
        transferWaySourceEntity.setSourceId(transferGenWaySourceDTO.getSourceId());
        transferWaySourceEntity.setSeq(transferGenWaySourceDTO.getSeq());
        transferWaySourceEntity.setStatus(transferGenWaySourceDTO.getStatus());
        return transferWaySourceDao.save(transferWaySourceEntity);
    }

    /**
     * 修改推广平台下的推广方式
     */
    @Override
    public int updateWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO) {
        TransferGenWayEntity transferGenWayEntity = transferGenWayDao.findOne(transferGenWaySourceDTO.getWayId());
        transferGenWayEntity.setGenWay(transferGenWaySourceDTO.getWayName());
        transferGenWayDao.update(transferGenWayEntity);

        TransferWaySourceEntity transferWaySourceEntity = transferWaySourceDao.findOne(transferGenWaySourceDTO.getId());
        transferWaySourceEntity.setSourceId(transferGenWaySourceDTO.getSourceId());
        transferWaySourceEntity.setStatus(transferGenWaySourceDTO.getStatus());
        transferWaySourceEntity.setSeq(transferGenWaySourceDTO.getSeq());
        return transferWaySourceDao.update(transferWaySourceEntity);
    }

}
