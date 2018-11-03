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

    /**
     * 分页查询推广平台下的推广方式
     */
    @Override
    public List<TransferWaySourceEntity> findPageGenWay(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return transferWaySourceDao.listPageGenWay(pageQuery);
    }

    /**
     * 根据wayId和sourceId查询是否存在重复数据
     */
    @Override
    public TransferWaySourceEntity findByWayIdAndSourceId(Long wayId, Long sourceId) {
        return baseDao.findByWayIdAndSourceId(wayId,sourceId);
    }

}
