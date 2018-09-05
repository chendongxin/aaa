package com.hqjy.transfer.allot.service.impl;

import com.hqjy.transfer.allot.constant.AllotCode;
import com.hqjy.transfer.allot.dao.AllotCustRepeatDao;
import com.hqjy.transfer.allot.dao.AllotCustomerDao;
import com.hqjy.transfer.allot.dao.AllotProcessDao;
import com.hqjy.transfer.allot.dao.AllotUrlAreaDao;
import com.hqjy.transfer.allot.exception.MqException;
import com.hqjy.transfer.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.transfer.allot.model.entity.AllotCustRepeatEntity;
import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;
import com.hqjy.transfer.allot.model.entity.AllotProcessEntity;
import com.hqjy.transfer.allot.model.entity.AllotUrlAreaEntity;
import com.hqjy.transfer.allot.service.AllotContactService;
import com.hqjy.transfer.allot.service.BizAllotService;
import com.hqjy.transfer.allot.service.BizHandleService;
import com.hqjy.transfer.common.base.constant.Constant;
import com.hqjy.transfer.common.base.utils.PojoConvertUtil;
import com.hqjy.transfer.common.redis.utils.RedisKeys;
import com.hqjy.transfer.common.redis.utils.RedisLockUtils;
import com.hqjy.transfer.common.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hqjy.transfer.common.base.constant.Constant.BizSource.*;
import static com.hqjy.transfer.common.base.constant.SystemId.User.NO_CREATE_ID;
import static java.util.regex.Pattern.compile;

/**
 * 商机消息处理业务入口
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 15:11
 */
@Service
@Slf4j
public class BizHandleServiceImpl implements BizHandleService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private RedisLockUtils redisLockUtils;

    @Autowired
    private AllotContactService allotContactService;

    @Autowired
    private BizAllotService bizAllotService;

    @Autowired
    private AllotCustomerDao allotCustomerDao;

    @Autowired
    private AllotUrlAreaDao allotUrlAreaDao;


    @Autowired
    private AllotCustRepeatDao allotCustRepeatDao;

    @Autowired
    private AllotProcessDao allotProcessDao;

/*    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private BizUserExtendService bizUserExtendService;*/

    /**
     * 商机消息分配逻辑
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean process(String msgId, AllotCustomerEntity customer) {
        String phone = customer.getPhone();
        // 处理并发问题，10秒内的重复商机不进行处理
        if (StringUtils.isNotEmpty(phone) && !redisLockUtils.setLock(RedisKeys.Allot.phoneLook(phone), String.valueOf(RandomUtils.nextLong()), 10000L)) {
            throw new MqException(AllotCode.BIZ_CONCURRENCY);
        }
        // 处理url太长
        if (StringUtils.isNotEmpty(customer.getUrl()) && customer.getUrl().length() > 2083) {
            customer.setUrl(customer.getUrl().substring(0, 2083));
        }
        // 处理 name 太长
        if (StringUtils.isNotEmpty(customer.getName()) && customer.getName().length() > 50) {
            customer.setName(customer.getName().substring(0, 49));
        }
        // 商机来源，进行数据初始化
        Integer source = customer.getSource();
        // 创建人不存在，设置默认系统创建人
        customer.setCreateId(Optional.ofNullable(customer.getCreateId()).orElse(NO_CREATE_ID.getValue()));
        // 如果没有姓名，设置为未知
        customer.setName(StringUtils.isNotEmpty(customer.getName()) ? customer.getName() : "未知");
        // 状态默认潜在
        customer.setStatus(Constant.CustomerStatus.POTENTIAL.getValue());
        // 联系次数默认1
        customer.setConsult(1);

        // 来自小能客服，分配类型为区域
        if (CUSTOMER_SERVICE.equals(source)) {
            log.debug("来自小能客服，分配类型为区域，Creator:{}，AreaId:{}", customer.getCreator(), customer.getAreaId());
            // 查询人员映射表
           /* Optional<BizUserExtendEntity> userExtend = Optional.ofNullable(bizUserExtendService.findByXnId(customer.getCreator()));
            // 如果人员存在，则设置
            userExtend.ifPresent(bizUserExtendEntity -> customer.setCreateId(bizUserExtendEntity.getUserId()));*/
        }
        // 来自官网，分配类型为区域
        else if (OFFICIAL.equals(source)) {
            // 官网延时消费
            if (StringUtils.isNotEmpty(msgId)) {
             /*   // 删除这个手机的锁
                redisUtils.delete(RedisKeys.Allot.phoneLook(phone));
                // 获取配置时间
                Long delaySec = sysConfigService.getConfig(ConfigConstant.ALLOT_DELAY_SEC, Long.class);
                long times = Optional.ofNullable(delaySec).orElse(300L);
                // 设置key，超时时间 ,默认 5 * 60 秒
                redisUtils.set(RedisKeys.Allot.delayKey(msgId), msgId, times);
                // 发送redis
                redisUtils.set(RedisKeys.Allot.delayMessage(msgId), customer);
                log.debug("来自官网延时{}秒后消费:{}", times, JsonUtil.toJson(customer));*/
                return true;
            } else {
                // 根据url映射，获取分配区域
                String url = customer.getUrl();
                if (StringUtils.isNotEmpty(url)) {
                    //正则表达式，取=和|之间的字符串，不包括=和|
                    Pattern p = compile("http://(.*?).html");
                    Matcher m = p.matcher(url);
                    while (m.find()) {
                        //m.group(1)不包括这两个字符
                        url = m.group(1);
                    }
                    Pattern p2 = compile("https://(.*?).html");
                    Matcher m2 = p2.matcher(url);
                    while (m2.find()) {
                        //m.group(1)不包括这两个字符
                        url = m2.group(1);
                    }
                    //  查询url映射区域, 如果是映射到人员，需要人员的部门
                    AllotUrlAreaEntity urlArea = allotUrlAreaDao.findOneByUrl(url);
                    customer.setAreaId(Optional.ofNullable(urlArea).map(AllotUrlAreaEntity::getAreaId).orElse(null));
                    customer.setDeptId(Optional.ofNullable(urlArea).map(AllotUrlAreaEntity::getDeptId).orElse(null));
                    customer.setUserId(Optional.ofNullable(urlArea).map(AllotUrlAreaEntity::getUserId).orElse(null));
                }
                log.debug("来自官网:url:{},areaId:{},deptId:{},userId:{}", url, customer.getAreaId(), customer.getDeptId(), customer.getUserId());
            }
        }
        //来自文件导入，分配类型有多种，按区域，按部门，按人员
        else if (FILEIMPORT.equals(source)) {

        }
        //其他来源不处理
        else {
            //抛出异常，无法识别商机来源
            throw new MqException(AllotCode.MQ_SOURCE_ERROR);
        }

        // 商机处理开始
        // 先直接保存用户信息，获取用户编号
        allotCustomerDao.save(customer);

        //保存用户联系方式
        ContactSaveResultDTO resultDTO = allotContactService.saveDetail(customer);

        //没有任何联系方式
        if (resultDTO == null) {
            //删除客户信息
            allotCustomerDao.delete(customer.getCustomerId());
            //抛出没有任何联系方式异常
            throw new MqException(AllotCode.BIZ_CONTACT_ERROR);
        }

        Long processId;

        // 首次咨询处理
        if (resultDTO.getContacStatus()) {
            log.debug("首次咨询流程分配");
            //首次咨询流程分配
            processId = bizAllotService.firstBizAllot(resultDTO, customer);
        }
        //二次咨询处理
        else {
            log.debug("二次咨询流程分配");
            //二次咨询流程分配
            processId = bizAllotService.repeatBizAllot(resultDTO, customer);
        }

        if (processId != null) {
            updateDeptIdAndUserId(processId, resultDTO, customer);
            return true;
        }

        return false;
    }

    /**
     * 分配完成后续操作，保存重单商机，更新客户表,异步保存到NC
     */
    private void updateDeptIdAndUserId(long processId, ContactSaveResultDTO resultDTO, AllotCustomerEntity customer) {
        // 查询本流程信息
        AllotProcessEntity process = allotProcessDao.findOne(processId);

        // 二次咨询处理，保存重单商机
        if (!resultDTO.getContacStatus()) {
            log.debug("记录到客户二次咨询表");
            //记录到客户二次咨询表
            AllotCustRepeatEntity custRepeatEntity = PojoConvertUtil.convert(customer, AllotCustRepeatEntity.class);
            custRepeatEntity.setCustomerId(resultDTO.getOldCustomerId());
            custRepeatEntity.setDeptId(process.getDeptId());
            custRepeatEntity.setUserId(process.getUserId());
            allotCustRepeatDao.save(custRepeatEntity);
        }

        // 冗余部门和人员还有联系方式到主表
        AllotCustomerEntity customerEntity = new AllotCustomerEntity();
        customerEntity.setCustomerId(process.getCustomerId());
        customerEntity.setDeptId(process.getDeptId());
        customerEntity.setUserId(process.getUserId());
        // 设置主联系方式,只能是手机
        customerEntity.setContact(Constant.CustomerContactType.PHONE.equals(resultDTO.getType()) ? resultDTO.getDetail() : null);
        // 分配时间
        customerEntity.setAllotTime(process.getCreateTime());
        allotCustomerDao.updateProcessInfo(customerEntity);

        // 发送到redis消息队列，异步处理，不关心处理结果
    /*    NcBizSaveParamDTO ncBizSaveDTO = PojoConvertUtil.convert(customer, NcBizSaveParamDTO.class);
        ncBizSaveDTO.setCustomerId(process.getCustomerId());
        ncBizSaveDTO.setUserId(process.getUserId());
        ncBizSaveDTO.setTel(customerEntity.getContact());
        ncBizSaveDTO.setSaleType(customer.getSaleType());
        ncBizSaveDTO.setName(customer.getName());
        ncBizSaveDTO.setLogurl(customer.getUrl());
        ncBizSaveDTO.setCreator(customer.getCreator());
        ncBizSaveDTO.setNote(customer.getMemo());
        ncBizSaveDTO.setLxqq(customer.getQq());
        listOperations.leftPush(RedisKeys.Nc.SAVE, JsonUtil.toJson(ncBizSaveDTO));*/
    }
}
