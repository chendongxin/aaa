package com.hqjy.mustang.transfer.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerReservationDao;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import com.hqjy.mustang.transfer.crm.service.NcService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerReservationService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.*;

/**
 * @author xyq 2018年10月9日14:22:01
 */
@Service
@Slf4j
public class TransferCustomerReservationServiceImpl extends BaseServiceImpl<TransferCustomerReservationDao, TransferCustomerReservationEntity, Long> implements TransferCustomerReservationService {


    private TransferCustomerService transferCustomerService;
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    private NcService ncService;

    @Autowired
    public void setNcService(NcService ncService) {
        this.ncService = ncService;
    }

    @Autowired
    public void setTransferCustomerService(TransferCustomerService transferCustomerService) {
        this.transferCustomerService = transferCustomerService;
    }

    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }

    @Override
    public List<TransferCustomerReservationEntity> findPage(PageQuery pageQuery) {
        if (isGeneralSeat()) {
            pageQuery.put("userId", getUserId());
        }
        if (isSuperAdmin()) {
            return super.findPage(pageQuery);
        }
        //获取当前用户的部门以及子部门
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            String deptIds = String.valueOf(x);
            ids.add(deptIds);
        });
        pageQuery.put("userAllDeptId", StringUtils.listToString(ids));
        transferCustomerService.formatQueryTime(pageQuery);
        return super.findPage(pageQuery);
    }

    @Override
    public int update(TransferCustomerReservationEntity entity) {
        if (entity.getVisitStatus() == 0) {
            if (entity.getIntention() != null || entity.getValidVisit() != null) {
                log.error("业务操作错误：该预约单未上门不应该填写意向度或者有效上门");
                throw new RRException("业务操作错误：该预约单未上门不应该填写意向度或者有效上门");
            }
        }
        entity.setUpdateUserId(getUserId()).setUpdateUserName(getUserName()).setUpdateTime(new Date()).setVisitTime(new Date());
        return super.update(entity);
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
            reservationEntity.setProId(customerEntity.getProId())
                    .setCreateUserId(getUserId()).setCreateUserName(getUserName()).setCreateTime(new Date());
            super.save(reservationEntity);
            //更新客户主表客户状态为预约状态
            customerEntity.setStatus(Constant.CustomerStatus.RESERVATION.getValue())
                    .setUpdateUserId(getUserId()).setUpdateUserName(getUserName()).setUpdateTime(new Date());
            transferCustomerService.update(customerEntity);
            return R.ok();
        } catch (Exception e) {
            log.error("预约处理异常：{}" + e.getMessage());
            throw new RRException(StatusCode.BIZ_CUSTOMER_RESERVE_EXCEPTION);
        }
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public R transferToNc(TransferCustomerReservationEntity reservationEntity) {
        try {
            TransferCustomerEntity customerEntity = transferCustomerService.findOne(reservationEntity.getCustomerId());

            if (!this.getNcId(customerEntity)) {
                return R.error(StatusCode.BIZ_CUSTOMER_TRANSFER_TO_NC_NOT_NC_ID);
            }
            //更新预约单对象信息
            reservationEntity.setStatus(1).setUpdateUserId(getUserId()).setUpdateUserName(getUserName())
                    .setUpdateTime(new Date());
            baseDao.update(reservationEntity);
            return R.ok();
        } catch (Exception e) {
            log.error("《==============转移NC处理异常=============》");
            e.printStackTrace();
            throw new RRException(StatusCode.BIZ_CUSTOMER_TRANSFER_TO_NC_EXCEPTION);
        }
    }

    /**
     * 请求NC转移接口
     *
     * @param customerEntity 商机对象
     * @return 返回请求结果
     */
    private boolean getNcId(TransferCustomerEntity customerEntity) {

        NcBizSaveResultDTO saveResult = ncService.requestNcSave(
                new NcBizSaveParamDTO()
                        .setTel(customerEntity.getPhone())
                        .setTrue_name("自考集训基地")
                        .setName(customerEntity.getName())
        );
        log.info("转移NC时请求NC新增商机接口获取NCId结果：" + JSON.toJSONString(saveResult));
        if (saveResult.getCode() == 1) {
            transferCustomerService.update(customerEntity.setNcId(saveResult.getMsg()).setUpdateTime(new Date())
                    .setUpdateUserId(getUserId())
                    .setUpdateUserName(getUserName()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TransferCustomerReservationEntity> getReservationByCustomerId(Long customerId) {
        return baseDao.getReservationByCustomerId(customerId);
    }
}
