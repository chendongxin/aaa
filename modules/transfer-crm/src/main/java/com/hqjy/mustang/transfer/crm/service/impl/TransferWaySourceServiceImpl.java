package com.hqjy.mustang.transfer.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.dao.TransferWaySourceDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferWaySourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        TransferGenWayEntity transferGenWayEntity = transferGenWayDao.findOneByGenName(transferGenWaySourceDTO.getGenWay());
        TransferWaySourceEntity transferWaySourceEntity = new TransferWaySourceEntity();
        if (transferGenWayEntity == null) {
            transferGenWayEntity = new TransferGenWayEntity();
            transferGenWayEntity.setGenWay(transferGenWaySourceDTO.getGenWay());
            transferGenWayEntity.setStatus(transferGenWaySourceDTO.getStatus());
            transferGenWayEntity.setSeq(transferGenWaySourceDTO.getSeq());
            transferGenWayEntity.setCreateUserId(getUserId());
            transferGenWayEntity.setCreateUserName(getUserName());
            System.out.println("transferGenWayEntity = " + transferGenWayEntity);
            int count = transferGenWayDao.save(transferGenWayEntity);
            if (count > 0) {
                transferWaySourceEntity.setWayId(transferGenWayEntity.getWayId());
            } else {
                throw new RRException(StatusCode.DATABASE_SAVE_FAILURE);
            }
        } else {
            transferWaySourceEntity.setWayId(transferGenWayEntity.getWayId());
        }
        transferWaySourceEntity.setSourceId(transferGenWaySourceDTO.getSourceId());
        transferWaySourceEntity.setSeq(transferGenWaySourceDTO.getSeq());
        transferWaySourceEntity.setStatus(transferGenWaySourceDTO.getStatus());
        transferWaySourceEntity.setCreateUserId(getUserId());
        transferWaySourceEntity.setCreateUserName(getUserName());
        return transferWaySourceDao.save(transferWaySourceEntity);
    }

    /**
     * 修改推广平台下的推广方式
     */
    @Override
    public int update(TransferWaySourceEntity transferWaySourceEntity) {
        transferWaySourceEntity.setUpdateUserId(getUserId());
        transferWaySourceEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferWaySourceEntity);
    }

    /**
     * 分页查询推广平台下的推广方式
     */
    @Override
    public List<TransferWaySourceEntity> findPageSource(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return transferWaySourceDao.listPageSource(pageQuery);
    }

//    /**
//     * 修改推广平台下的推广方式
//     */
//    @Override
//    public int updateWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO) {
//        TransferGenWayEntity transferGenWayEntity = transferGenWayDao.findOne(transferGenWaySourceDTO.getWayId());
//        transferGenWayEntity.setGenWay(transferGenWaySourceDTO.getGenWay());
//        transferGenWayDao.update(transferGenWayEntity);
//
//        TransferWaySourceEntity transferWaySourceEntity = transferWaySourceDao.findOne(transferGenWaySourceDTO.getId());
//        transferWaySourceEntity.setSourceId(transferGenWaySourceDTO.getSourceId());
//        transferWaySourceEntity.setStatus(transferGenWaySourceDTO.getStatus());
//        transferWaySourceEntity.setSeq(transferGenWaySourceDTO.getSeq());
//        return transferWaySourceDao.update(transferWaySourceEntity);
//    }

}