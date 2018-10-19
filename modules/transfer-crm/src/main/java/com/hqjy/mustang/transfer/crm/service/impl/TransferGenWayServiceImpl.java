package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import com.hqjy.mustang.transfer.crm.service.TransferWaySourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenWayServiceImpl extends BaseServiceImpl<TransferGenWayDao, TransferGenWayEntity, Long> implements TransferGenWayService {

    @Autowired
    private TransferWaySourceService transferWaySourceService;
    /**
     * 增加推广方式
     */
    @Override
    public int save(TransferGenWayEntity transferGenWayEntity) {
        if (baseDao.findOneByGenName(transferGenWayEntity.getGenWay()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferGenWayEntity.setCreateUserId(getUserId());
        transferGenWayEntity.setCreateUserName(getUserName());
        return baseDao.save(transferGenWayEntity);
    }

    /**
     * 删除推广方式
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] wayIds) {
        List<Long> list = Arrays.asList(wayIds);
        for (Long wayId : list) {
            List<TransferGenWayEntity> genWayList = baseDao.findByParentId(wayId);
            if (genWayList.size() > 0) {
                throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
            }
        }
        return super.deleteBatch(wayIds);
    }

    /**
     * 修改推广方式
     */
    @Override
    public int update(TransferGenWaySourceDTO genWaySourceDTO) {
        if (genWaySourceDTO.getGenWay() == null) {
            return transferWaySourceService.update(new TransferWaySourceEntity().setSourceId(genWaySourceDTO.getSourceId())
                .setSeq(genWaySourceDTO.getSeq()).setStatus(genWaySourceDTO.getStatus()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
            );
        }
        TransferGenWayEntity transferGenWayEntity = new TransferGenWayEntity().setGenWay(genWaySourceDTO.getGenWay()).setSeq(genWaySourceDTO.getSeq())
                .setStatus(genWaySourceDTO.getStatus()).setCreateUserId(getUserId()).setCreateUserName(getUserName());
        int count = super.save(transferGenWayEntity);
        if (count > 0) {
            count = transferWaySourceService.save(
                    new TransferWaySourceEntity()
                        .setWayId(transferGenWayEntity.getWayId()).setSourceId(genWaySourceDTO.getSourceId()).setSeq(genWaySourceDTO.getSeq())
                        .setStatus(genWaySourceDTO.getStatus()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
            );
            if (count > 0) {
                Long[] ids = new Long[1];
                ids[0] = genWaySourceDTO.getId();
                count = transferWaySourceService.deleteBatch(ids);
            }
        }
        return count;
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
