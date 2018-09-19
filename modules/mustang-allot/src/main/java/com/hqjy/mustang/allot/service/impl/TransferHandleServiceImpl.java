package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.constant.AllotCode;
import com.hqjy.mustang.allot.dao.*;
import com.hqjy.mustang.allot.exception.MqException;
import com.hqjy.mustang.allot.feign.SysMessageApiService;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerRepeatEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotProcessEntity;
import com.hqjy.mustang.allot.service.AbstractAllotService;
import com.hqjy.mustang.allot.service.AbstractHandleService;
import com.hqjy.mustang.allot.service.TransferAllotContactService;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

import static com.hqjy.mustang.common.base.constant.Constant.CustomerContactType.*;
import static com.hqjy.mustang.common.base.constant.SystemId.User.NO_CREATE_ID;

/**
 * 招转商机消息处理业务入口
 *
 * @author : heshuangshuang
 * @date : 2018/9/13 16:08
 */
@Service(value = "transferHandleService")
@Slf4j
public class TransferHandleServiceImpl extends AbstractHandleService<TransferAllotCustomerEntity> {

    @Autowired
    private RedisLockUtils redisLockUtils;

    @Autowired
    private TransferAllotCustomerDao transferAllotCustomerDao;

    @Autowired
    private TransferAllotCustomerDetailDao transferAllotCustomerDetailDao;

    @Autowired
    private TransferAllotContactService transferAllotContactService;

    @Autowired
    private TransferAllotCustomerContactDao allotContactDao;

    @Autowired
    private SysMessageApiService sysMessageApiService;

    @Autowired
    private TransferAllotProcessDao transferAllotProcessDao;

    @Autowired
    private TransferAllotCustomerRepeatDao allotCustRepeatDao;

    @Resource(name = "transferAllotService")
    private AbstractAllotService transferAllotService;


    /**
     * 预处理,保存用户基本信息和联系方式
     * 通过联系方式判断重单情况
     */
    @Override
    public ContactSaveResultDTO pretreatment(TransferAllotCustomerEntity customerEntity) {

        String phone = customerEntity.getPhone();

        // 处理并发问题，10秒内的重复商机不进行处理
        if (StringUtils.isNotEmpty(phone) && !redisLockUtils.setLock(RedisKeys.Allot.phoneLook(phone), String.valueOf(RandomUtils.nextLong()), 10000L)) {
            throw new MqException(AllotCode.BIZ_CONCURRENCY);
        }

        // 处理 name 太长
        if (StringUtils.isNotEmpty(customerEntity.getName()) && customerEntity.getName().length() > 50) {
            customerEntity.setName(customerEntity.getName().substring(0, 49));
        }

        // 商机来源，进行数据初始化
        // 创建人不存在，设置默认系统创建人
        customerEntity.setCreateUserId(Optional.ofNullable(customerEntity.getCreateUserId()).orElse(NO_CREATE_ID.getValue()));
        // 如果没有姓名，设置为未知
        customerEntity.setName(StringUtils.isNotEmpty(customerEntity.getName()) ? customerEntity.getName() : "未知");
        // 状态默认潜在
        customerEntity.setStatus(Constant.CustomerStatus.POTENTIAL.getValue());
        // 推广方式
        customerEntity.setGetWay(0);
        // 设置分配时间为当前时间
        customerEntity.setAllotTime(new Date());

        // 商机处理开始
        // TODO 先直接保存用户信息，获取用户编号，不写入nc_id，phone，we_chat，qq， land_line
        transferAllotCustomerDao.save(customerEntity);

        // 保存用户联系方式
        ContactSaveResultDTO resultDTO = transferAllotContactService.saveDetail(customerEntity);
        // 没有任何联系方式
        if (resultDTO == null) {
            //删除客户信息
            transferAllotCustomerDao.delete(customerEntity.getCustomerId());
            //抛出没有任何联系方式异常
            throw new MqException(AllotCode.BIZ_CONTACT_ERROR);
        }
        return resultDTO;
    }

    /**
     * 分配,设置用户ID和部门ID
     */
    @Override
    public void allot(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customerEntity) {
        // 分配处理
        transferAllotService.allot(resultDTO, customerEntity);
    }

    /**
     * 流程保存到数据库
     */
    @Override
    public Long saveProcess(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customerEntity) {
        TransferAllotProcessEntity allotProcess = new TransferAllotProcessEntity();
        allotProcess.setCustomerId(customerEntity.getCustomerId());
        // 设置部门
        allotProcess.setDeptId(customerEntity.getDeptId());
        // 设置分配人员
        allotProcess.setUserId(customerEntity.getUserId());
        // 设置状态有效
        allotProcess.setActive(1);
        // 0 默认为系统
        allotProcess.setCreateUserId(0L);
        // 联系次数默认0
        allotProcess.setFollowCount(0);
        int count;
        //首次咨询
        if (resultDTO.getContacStatus()) {
            allotProcess.setMemo("首次咨询，自动分配");
            // 流程的到期时间，默认为0天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.TRANSFER_ALLOT_FIRST_TIMEOUT, 0));
        }

        //二次咨询
        else {
            allotProcess.setMemo("二次咨询，自动分配");
            // 流程的到期时间，默认为3天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.TRANSFER_ALLOT_REPEAT_TIMEOUT, 3));
        }

        //保存流程
        count = transferAllotProcessDao.saveActive(allotProcess);

        // 修改其他流程为未激活状态, 因为customerId加了索引，并发时会锁表，单独进行update
        if (count > 0) {
            transferAllotProcessDao.updateNotActivated(customerEntity.getCustomerId());
            transferAllotProcessDao.updateActive(allotProcess.getProcessId());
        }
        return allotProcess.getProcessId();
    }


    /**
     * 重单商机保存处理
     */
    @Override
    protected void repeatSave(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customer) {
        // 二次咨询处理，保存重单商机
        if (!resultDTO.getContacStatus()) {
            log.debug("记录到客户二次咨询表");
            //记录到客户二次咨询表
            TransferAllotCustomerRepeatEntity custRepeatEntity = PojoConvertUtil.convert(customer, TransferAllotCustomerRepeatEntity.class);
            custRepeatEntity.setCustomerId(resultDTO.getOldCustomerId());
            custRepeatEntity.setDeptId(customer.getDeptId());
            custRepeatEntity.setUserId(customer.getUserId());
            allotCustRepeatDao.save(custRepeatEntity);
        }
    }

    /**
     * 发送webSocket消息,招转首次，二次都是视为首次咨询
     */
    @Override
    public void sendMessage(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customer) {
        if (customer.getUserId() != null) {
            TransferAllotCustomerContactEntity contactEntity = allotContactDao.findPrimary(customer.getCustomerId());
            StringBuilder content = new StringBuilder();
            if (contactEntity != null) {
                if (PHONE.equals(contactEntity.getType())) {
                    content.append(PHONE.getLabel());
                } else if (LAND_LINE.equals(contactEntity.getType())) {
                    content.append(PHONE.getLabel());
                } else if (WEI_XIN.equals(contactEntity.getType())) {
                    content.append(WEI_XIN.getLabel());
                } else if (QQ.equals(contactEntity.getType())) {
                    content.append(QQ.getLabel());
                }
                content.append(":").append(contactEntity.getDetail());
            }
            // 首次咨询
            sysMessageApiService.sendMessage(new MessageSendVO(customer.getUserId(), "首次咨询", content.toString(), contactEntity));
        }

    }

    /**
     * 分配完成后续操作，保存重单商机，更新客户表,异步保存到NC
     */
    @Override
    protected void finalTreatment(Long processId, TransferAllotCustomerEntity customer) {
        // 查询本流程信息
        TransferAllotProcessEntity process = transferAllotProcessDao.findOne(processId);

        // 冗余部门和人员还有联系方式到主表
        TransferAllotCustomerEntity customerEntity = new TransferAllotCustomerEntity();
        customerEntity.setCustomerId(customer.getCustomerId());
        customerEntity.setDeptId(process.getDeptId());
        customerEntity.setUserId(process.getUserId());
        // 设置主联系方式,只能是手机
        customerEntity.setPhone(customer.getPhone());
        // 更新分配时间
        // customerEntity.setAllotTime(process.getCreateTime());
        transferAllotCustomerDao.updateProcessInfo(customerEntity);
        // 保存简历详情
        transferAllotCustomerDetailDao.saveCustomer(customer);

        // 发送到redis消息队列，异步处理，不关心处理结果 TODO

    }
}
