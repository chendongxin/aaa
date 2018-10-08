package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDealDao;
import com.hqjy.mustang.transfer.crm.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.dto.NcDealMsgDTO;
import com.hqjy.mustang.transfer.crm.model.entity.SysDeptEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDealEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDealService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;

/**
 * @author xyq
 * @date create on 2018/9/30
 * @apiNote
 */
@Service
@Slf4j
public class TransferCustomerDealServiceImpl extends BaseServiceImpl<TransferCustomerDealDao, TransferCustomerDealEntity, Long> implements TransferCustomerDealService {


    private static final String Y = "Y";
    private static final String CUSTOMER_ID = "customerId";

    private TransferCustomerContactService transferCustomerContactService;
    private TransferCustomerService transferCustomerService;
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    private SysDeptServiceFeign sysDeptServiceFeign;

    @Autowired
    public void setSysDeptServiceFeign(SysDeptServiceFeign sysDeptServiceFeign) {
        this.sysDeptServiceFeign = sysDeptServiceFeign;
    }

    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }

    @Autowired
    public void setTransferCustomerContactService(TransferCustomerContactService transferCustomerContactService) {
        this.transferCustomerContactService = transferCustomerContactService;
    }

    @Autowired
    public void setTransferCustomerService(TransferCustomerService transferCustomerService) {
        this.transferCustomerService = transferCustomerService;
    }

    @Override
    public List<TransferCustomerDealEntity> findPage(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, CUSTOMER_ID);
        if (customerId != null && MapUtils.getLong(pageQuery, CUSTOMER_ID).equals(-1L)) {
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


    /**
     * 处理NC成交业务
     *
     * @param ncDeal 消息
     * @return 返回处理结果
     * @author xyq
     * @date create on 2018/9/30
     */
    @Override
    @SysLog("成交回推结果处理")
    @Transactional(rollbackFor = Exception.class)
    public int processDealMsg(NcDealMsgDTO ncDeal) {
        // 根据NC主键查询客户信息
        TransferCustomerEntity customerEntity = transferCustomerService.getByNcId(ncDeal.getNcPk());
        if (customerEntity != null) {
            // 成交
            TransferCustomerDealEntity deal = new TransferCustomerDealEntity();
            if (Y.equals(ncDeal.getState())) {
                customerEntity.setStatus(Constant.CustomerStatus.DEAL.getValue());
                String userName = customerEntity.getUserId() != null ? customerEntity.getUserName() : customerEntity.getLastUserName();

                baseDao.save(deal.setCustomerId(customerEntity.getCustomerId())
                        .setCreateUserId(SystemId.User.NO_CREATE_ID.getValue())
                        .setCreateUserName("系统")
                        .setCreateTime(ncDeal.getMinRegDate())
                        .setProId(customerEntity.getProId())
                        .setDeptId(customerEntity.getLastUserDeptId())
                        .setUserId(customerEntity.getUserId() == null ? customerEntity.getLastUserId() : customerEntity.getUserId())
                        .setUserName(userName)
                );
                log.info("NC成交订单，修改客户状态为成交，并写入成交表");
            } else {
                customerEntity.setStatus(Constant.CustomerStatus.RESERVATION.getValue());
                baseDao.updateIsDelete(deal.setUpdateUserId(SystemId.User.NO_CREATE_ID.getValue())
                        .setUpdateUserName("系统")
                        .setUpdateTime(new Date())
                        .setDeleted(Boolean.TRUE)
                        .setDeleteUserId(SystemId.User.NO_CREATE_ID.getValue())
                        .setDeleteTime(new Date()));
                log.info("NC撤销订单，修改客户状态为预约，并设置成交记录为删除状态");
            }
            return transferCustomerService.update(customerEntity.setUpdateTime(new Date())
                    .setUpdateUserId(SystemId.User.NO_CREATE_ID.getValue())
                    .setUpdateUserName("系统"));
        }
        return 0;
    }
}
