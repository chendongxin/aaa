package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerInvalidDao;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerInvalidEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerInvalidService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.*;

@Service
@Slf4j
public class TransferCustomerInvalidServiceImpl extends BaseServiceImpl<TransferCustomerInvalidDao, TransferCustomerInvalidEntity, Long> implements TransferCustomerInvalidService {

    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private TransferCustomerContactService transferCustomerContactService;
    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    /**
     * 设置客户无效
     */
    @Override
    @Transactional
    public R setCustomerInvalid(TransferCustomerDTO dto) {
        try {
            //添加无效客户记录
            super.save(new TransferCustomerInvalidEntity()
                    .setCreateUserId(getUserId())
                    .setCreateUserName(getUserName())
                    .setCustomerId(dto.getCustomerId())
                    .setStatus(dto.getStatus())
                    .setType(dto.getType())
                    .setMemo(dto.getMemo()));
            TransferCustomerEntity customerEntity = transferCustomerService.findOne(dto.getCustomerId());
            //更新客户状态为无效状态
            customerEntity.setUpdateUserId(getUserId());
            customerEntity.setUpdateUserName(getUserName());
            if (dto.getStatus() == 1) {
                customerEntity.setStatus(Constant.CustomerStatus.FAILED_VALID.getValue());
            } else {
                customerEntity.setStatus(Constant.CustomerStatus.FAILED_INVALID.getValue());
            }
            transferCustomerService.update(customerEntity);
            return R.ok();
        } catch (Exception e) {
            throw new RRException(StatusCode.BIZ_CUSTOMER_INVALID_EXCEPTION);
        }
    }

    @Override
    public List<TransferCustomerInvalidEntity> findPage(PageQuery query) {
        transferCustomerContactService.setCustomerIdByContact(query);
        Long customerId = MapUtils.getLong(query, "customerId");
        if (customerId != null && MapUtils.getLong(query, "customerId").equals(-1L)) {
            return null;
        }
        if (isGeneralSeat()) {
            query.put("userId", getUserId());
        }
        if (isSuperAdmin()) {
            return super.findPage(query);
        }
        //获取当前用户的部门以及子部门
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        query.put("userAllDeptId", StringUtils.listToString(ids));
        transferCustomerService.formatQueryTime(query);
        return super.findPage(query);
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public R returnToPrivate(Long customerId) {
        try {
            TransferCustomerEntity customerEntity = transferCustomerService.findOne(customerId);
            //更新客户状态为潜在状态
            customerEntity.setStatus(Constant.CustomerStatus.POTENTIAL.getValue())
                    .setUpdateUserId(getUserId()).setUpdateUserName(getUserName()).setUpdateTime(new Date());
            transferCustomerService.update(customerEntity);
            baseDao.deleteBatch(baseDao.getCustomerByCustomerId(customerId).stream().toArray(Long[]::new));
            return R.ok();
        } catch (Exception e) {
            log.error("客户无效转私海处理异常" + e.getMessage());
            throw new RRException(StatusCode.BIZ_CUSTOMER_INVALID_TO_PRIVATE_EXCEPTION);
        }
    }

}
