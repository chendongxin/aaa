package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.dao.TransferSourceDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferSourceServiceImpl extends BaseServiceImpl<TransferSourceDao, TransferSourceEntity, Long> implements TransferSourceService {

    /**
     * 分页查询
     */
    @Override
    public List<TransferSourceEntity> findPage(PageQuery pageQuery) {
        List<TransferSourceEntity> list = super.findPage(pageQuery);
        return list;
    }

    /**
     * 增加一条来源
     */
//    @Override
//    public int save(TransferSourceEntity transferSourceEntity) {
//        String userId =
//    }

}
