package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerReservationDao;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerReservationService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
@Slf4j
public class TransferCustomerReservationServiceImpl extends BaseServiceImpl<TransferCustomerReservationDao, TransferCustomerReservationEntity, Long> implements TransferCustomerReservationService {

    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private TransferCustomerContactService transferCustomerContactService;
    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Override
    public List<TransferCustomerReservationEntity> findPage(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, "customerId");
        if (customerId != null && MapUtils.getLong(pageQuery, "customerId").equals(-1L)) {
            return null;
        }
        //获取当前用户的部门以及子部门
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        pageQuery.put("userAllDeptId", StringUtils.listToString(ids));
        transferCustomerService.formatQueryTime(pageQuery);
        return super.findPage(pageQuery);
    }

    /**
     * 预约客户
     */
    @Override
    @Transactional(rollbackFor = RRException.class)
    public R reserveCustomer(TransferCustomerReservationEntity reservationEntity) {
        try {
            TransferCustomerEntity customerEntity = transferCustomerService.findOne(reservationEntity.getCustomerId());
            if (customerEntity.getUserId() == null) {
                return R.error("预约失败：当前客户为公有客户，不能进行预约！");
            }
            //添加客户预约表
            reservationEntity.setCreateUserId(getUserId());
            reservationEntity.setCreateUserName(getUserName());
            super.save(reservationEntity);
            //更新客户主表客户状态为预约状态
            customerEntity.setStatus(Constant.CustomerStatus.RESERVATION.getValue())
                    .setUpdateUserId(getUserId()).setUpdateUserName(getUserName());
            transferCustomerService.update(customerEntity);
            return R.ok();
        } catch (Exception e) {
            log.error("预约处理异常：{}" + e.getMessage());
            throw new RRException(StatusCode.BIZ_CUSTOMER_RESERVE_EXCEPTION);
        }
    }
}
