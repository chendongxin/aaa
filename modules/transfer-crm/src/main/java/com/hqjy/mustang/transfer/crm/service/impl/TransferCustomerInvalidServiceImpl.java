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
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
@Slf4j
public class TransferCustomerInvalidServiceImpl extends BaseServiceImpl<TransferCustomerInvalidDao, TransferCustomerInvalidEntity, Long> implements TransferCustomerInvalidService {

    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private TransferCustomerContactService transferCustomerContactService;
    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    @Autowired
    private TransferProcessService transferProcessService;

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

//    @Override
//    @Transactional(rollbackFor = RRException.class)
//    public R returnToPrivate(Long customerId) {
//        Date date = new Date();
//        try {
//            String error = "用户【" + getUserId() + "】退回私海【" + customerId + "】:";
//            TransferProcessEntity process = transferProcessService.getProcessByCustIdAndUserId(customerId);
//            TransferCustomerEntity customerEntity = transferCustomerService.findOne(customerId);
//            if (null == process) {
//                log.error(error + StatusCode.BIZ_PROCESS_INACTIVE_OR_NOT_PRIVATE.getMsg());
//                return;
//            }
//            if (!customerEntity.getStatus().equals(Constant.CustomerStatus.POTENTIAL.getValue())) {
//                log.error(error + StatusCode.BIZ_CUSTOMER_NOT_POTENTIAL.getMsg());
//                return;
//            }
//            //设置流程过期
//            int i = bizProcessService.disableProcessActive(process);
//            if (i == 0) {
//                log.error(error + StatusCode.BIZ_PROCESS_UPDATE_INACTIVE.getMsg());
//                return;
//            }
//            //新增激活状态的客户流程
//            int save = bizProcessService.save(new BizProcessEntity().setCreateTime(date).setMemo("无效退回私海操作")
//                    .setCustomerId(c).setDeptId(process.getDeptId()).setActive(Boolean.TRUE).setCreateId(getUserId()));
//            if (save == 0) {
//                log.error(error + StatusCode.BIZ_PROCESS_SAVE_FAULT.getMsg());
//                return;
//            }
//            //更新客户主表(同步激活状态流程)
//            int update = transferCustomerService.returnToCommon(new BizCustomerEntity().setAllotTime(date).setUpdateTime(date)
//                    .setCustomerId(c).setUpdateId(getUserId()));
//            if (update == 0) {
//                log.error(error + StatusCode.BIZ_CUSTOMER_UPDATE_FAULT.getMsg());
//            }
//            return R.ok();
//        } catch (Exception e) {
//            log.error("《=========私海退回公海异常，原因如下==========》,{}", e.getMessage());
//            e.printStackTrace();
//            throw new RRException(e.getMessage());
//        }
//        return null;
//    }

}
