package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDealDao;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDealEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDealService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;

@Service
public class TransferCustomerDealServiceImpl extends BaseServiceImpl<TransferCustomerDealDao, TransferCustomerDealEntity, Long> implements TransferCustomerDealService {

    @Autowired
    private TransferCustomerContactService transferCustomerContactService;
    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Override
    public List<TransferCustomerDealEntity> findPage(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, "customerId");
        if (customerId != null && MapUtils.getLong(pageQuery, "customerId").equals(-1L)) {
            return null;
        }
        transferCustomerService.formatQueryTime(pageQuery);
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        pageQuery.put("userAllDeptId", StringUtils.listToString(ids));
        return super.findPage(pageQuery);
    }
}
