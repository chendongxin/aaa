package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.dao.TransferWaySourceDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import com.hqjy.mustang.transfer.crm.service.TransferWaySourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenWayServiceImpl extends BaseServiceImpl<TransferGenWayDao, TransferGenWayEntity, Long> implements TransferGenWayService {

    @Autowired
    private TransferWaySourceService transferWaySourceService;
    @Autowired
    private TransferWaySourceDao transferWaySourceDao;


    /**
     * 保存推广平台下的推广方式
     */
    @Override
    public int saveWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO) {
        TransferGenWayEntity transferGenWayEntity = baseDao.findOneByGenName(transferGenWaySourceDTO.getGenWay());
        TransferWaySourceEntity transferWaySourceEntity = new TransferWaySourceEntity();
        if (transferGenWayEntity == null) {
            transferGenWayEntity = new TransferGenWayEntity().setGenWay(transferGenWaySourceDTO.getGenWay()).setStatus(transferGenWaySourceDTO.getStatus())
                    .setSeq(transferGenWaySourceDTO.getSeq()).setCreateUserId(getUserId()).setCreateUserName(getUserName());
            int count = baseDao.save(transferGenWayEntity);
            if (count > 0) {
                transferWaySourceEntity.setWayId(transferGenWayEntity.getWayId());
            } else {
                throw new RRException(StatusCode.DATABASE_SAVE_FAILURE);
            }
        } else {
            transferWaySourceEntity.setWayId(baseDao.findOneByGenName(transferGenWaySourceDTO.getGenWay()).getWayId());
            if (transferWaySourceService.findByWayIdAndSourceId(transferWaySourceEntity.getWayId(), transferGenWaySourceDTO.getSourceId()) != null) {
                throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
            }
        }
        return transferWaySourceDao.save(transferWaySourceEntity.setSourceId(transferGenWaySourceDTO.getSourceId())
                .setSeq(transferGenWaySourceDTO.getSeq()).setStatus(transferGenWaySourceDTO.getStatus()).setSign(0)
                .setCreateUserId(getUserId()).setCreateUserName(getUserName())
        );
    }

    /**
     * 删除指定平台下的推广方式
     */
    @Override
    public int delete(Long id) {
        if (transferWaySourceService.findOne(id).getSign() == 1) {
            throw new RRException(StatusCode.DATABASE_SELECT_USE);
        }
        return transferWaySourceService.delete(id);
    }

    /**
     * 修改指定平台下的推广方式
     */
    @Override
    public int update(TransferWaySourceEntity waySource) {
        return transferWaySourceService.update(waySource.setUpdateUserId(getUserId()).setUpdateUserName(getUserName()));
    }

    /**
     * 获取指定来源平台的推广方式
     */
    @Override
    public List<TransferGenWayEntity> findBySourceId(Long sourceId) {
        return baseDao.findBySourceId(sourceId);
    }

    /**
     * 获取所有推广方式
     */
    @Override
    public List<TransferGenWayEntity> getAllGenWayList() {
        return baseDao.getAllGenWayList();
    }

    /**
     * 获取不属于指定平台的推广方式
     */
    @Override
    public List<TransferGenWayEntity> findNotBySourceId(Long sourceId) {
        return baseDao.findNotBySourceId(sourceId);
    }

}
