package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.constant.AllotCode;
import com.hqjy.mustang.allot.dao.TransferAllotCustomerContactDao;
import com.hqjy.mustang.allot.dao.TransferAllotCustomerDao;
import com.hqjy.mustang.allot.dao.TransferAllotProcessDao;
import com.hqjy.mustang.allot.exception.MqException;
import com.hqjy.mustang.allot.feign.SysMessageApiService;
import com.hqjy.mustang.allot.feign.TransferKeywordApiService;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotProcessEntity;
import com.hqjy.mustang.allot.service.AbstractAllotService;
import com.hqjy.mustang.allot.service.AbstractHandleService;
import com.hqjy.mustang.allot.service.TransferAllotContactService;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
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
    private TransferAllotContactService transferAllotContactService;

    @Autowired
    private TransferAllotCustomerContactDao allotContactDao;

    @Autowired
    private SysMessageApiService sysMessageApiService;

    @Autowired
    private TransferAllotProcessDao transferAllotProcessDao;

    @Autowired
    private TransferKeywordApiService transferKeywordApiService;

    @Resource(name = "transferAllotService")
    private AbstractAllotService<TransferAllotCustomerEntity> transferAllotService;


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
        customerEntity.setStatus(0);
        // 推广方式，默认 1：主动
        customerEntity.setGetWay(1);
        // 设置分配时间为当前时间
        customerEntity.setAllotTime(new Date());
        customerEntity.setCreateUserId(Optional.ofNullable(customerEntity.getCreateUserId()).orElse(0L));
        customerEntity.setCreateUserName(Optional.ofNullable(customerEntity.getCreateUserName()).orElse("系统"));

        // 先直接保存用户信息，获取用户编号，不写入nc_id，phone，we_chat，qq， land_line
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
    public TransferAllotCustomerEntity allot(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customerEntity) {
        // 分配处理
        return transferAllotService.allot(resultDTO, customerEntity);
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
        allotProcess.setDeptName(customerEntity.getDeptName());
        // 设置分配人员
        allotProcess.setUserId(customerEntity.getUserId());
        allotProcess.setUserName(customerEntity.getUserName());
        // 设置状态有效
        allotProcess.setActive(1);
        // 0 默认为系统
        allotProcess.setCreateUserId(0L);
        // 联系次数默认0
        allotProcess.setFollowCount(0);
        allotProcess.setCreateUserId(customerEntity.getCreateUserId());
        allotProcess.setCreateUserName(customerEntity.getCreateUserName());
        int count;
        //首次咨询
        if (resultDTO.getContacStatus()) {
            allotProcess.setMemo("首次咨询，自动分配");
            // 流程的到期时间，默认为0天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.TRANSFER_ALLOT_FIRST_TIMEOUT, 15));
        }

        //二次咨询
        else {
            allotProcess.setMemo("二次咨询，自动分配");
            // 流程的到期时间，默认为3天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.TRANSFER_ALLOT_REPEAT_TIMEOUT, 15));
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
     * 发送webSocket消息,招转首次，二次都是视为首次咨询
     */
    @Override
    public void sendMessage(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity customer) {
        if (customer.getUserId() != null) {
            TransferAllotCustomerContactEntity contactEntity = allotContactDao.findPrimary(customer.getCustomerId());
            StringBuilder content = new StringBuilder();
            if (contactEntity != null) {
                if (PHONE.equals(contactEntity.getType())) {
                    content.append("电话");
                } else if (LAND_LINE.equals(contactEntity.getType())) {
                    content.append("座机");
                } else if (WE_CHAT.equals(contactEntity.getType())) {
                    content.append("微信");
                } else if (QQ.equals(contactEntity.getType())) {
                    content.append("QQ");
                }
                content.append("：").append(contactEntity.getDetail());
            }
            // 首次咨询,招转消息提示都是首次咨询
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
        customerEntity.setDeptName(process.getDeptName());
        customerEntity.setUserId(process.getUserId());
        customerEntity.setUserName(process.getUserName());
        customerEntity.setFirstUserId(customer.getFirstUserId());
        customerEntity.setFirstUserName(customer.getFirstUserName());
        customerEntity.setLastUserId(process.getUserId());
        customerEntity.setLastUserName(process.getUserName());

        // 设置联系方式
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setLandLine(customer.getLandLine());
        customerEntity.setWeChat(customer.getWeChat());
        customerEntity.setQq(customer.getQq());
        // 更新分配时间
        customerEntity.setAllotTime(process.getCreateTime());
        // 职位关键字获取
        customerEntity.setApplyKey(transferKeywordApiService.getKeyword(customer.getPositionApplied()));
        transferAllotCustomerDao.updateProcessInfo(customerEntity);
    }
}
