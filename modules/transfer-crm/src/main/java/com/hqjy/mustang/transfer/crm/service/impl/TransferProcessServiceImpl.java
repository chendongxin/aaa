package com.hqjy.mustang.transfer.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferProcessDao;
import com.hqjy.mustang.transfer.crm.feign.SysLogServiceFeign;
import com.hqjy.mustang.transfer.crm.model.entity.SysLogEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
@Slf4j
public class TransferProcessServiceImpl extends BaseServiceImpl<TransferProcessDao, TransferProcessEntity, Long> implements TransferProcessService {

    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private SysLogServiceFeign sysLogServiceFeign;

    /**
     * 获取当前流程为激活状态的流程数据
     *
     * @param customerId 客户ID
     * @return 返回流程对象
     */
    @Override
    public TransferProcessEntity getProcessByCustomerId(Long customerId) {
        return baseDao.getProcessByCustomerId(customerId);
    }

    /**
     * 设置客户流程过期
     *
     * @param entity 流程对象
     * @return 退回结果
     */
    @Override
    public int disableProcessActive(TransferProcessEntity entity) {
        return baseDao.disableProcessActive(entity);
    }

    @Override
    public List<TransferProcessEntity> getFirstAllotProcessBatch(String customerIds) {
        return baseDao.getFirstAllotProcessBatch(customerIds);
    }

    @Override
    public TransferProcessEntity getProcessByCustIdAndUserId(Long customerId) {
        return baseDao.getProcessByCustIdAndUserId(customerId);
    }


    /**
     * 查询当天用户拥有商机数量
     *
     * @param map 查询参数
     * @return 返回数量
     * @author gmm 2018-9-28 17:28:24
     */
    @Override
    public int countHasTotal(Map<String, Object> map) {
        return baseDao.countHasTotal(map);
    }

    /**
     * 获取公海当前流程为激活状态的数据
     */
    @Override
    public TransferProcessEntity getProcessByPublicCustomerId(Long customerId) {
        return baseDao.getProcessByPublicCustomerId(customerId);
    }

    /**
     * 根据用户id更新用户非成交商机到公海
     *
     * @author HSS 2018-08-11
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserTransferProcessToPublic(Long userId, boolean sign) {
        List<TransferProcessEntity> list = baseDao.findUserActive(userId, sign);
        if (list.size() > 0) {
            list.forEach(process -> {
                TransferProcessEntity newProcess = new TransferProcessEntity()
                        .setActive(false)
                        .setDeptId(process.getDeptId())
                        .setDeptName(process.getDeptName())
                        .setFollowCount((long)0)
                        .setMemo(sign ? "部门变更，原部门商机进入公海" : "人员删除，商机进入公海")
                        .setCreateUserId(getUserId())
                        .setCreateUserName(getUserName())
                        .setCustomerId(process.getCustomerId());
                baseDao.save(newProcess);
            });
            //设置流程过期
            baseDao.disableProcessActiveBatch(StringUtils.listToString(list.stream().map(p -> String.valueOf(p.getProcessId())).collect(Collectors.toList())));
        }
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public void RecyclingCustomer() throws Exception {
        long beginTime = System.currentTimeMillis();
        String time = DateUtils.format(new Date()) + Constant.CUSTOMER_RECYCLE_TIME;
        List<TransferProcessEntity> list = this.findListIsActive(time);
        if (!list.isEmpty()) {
            //记录系统回收日志
            List<String> stringList = new ArrayList<>();
            list.forEach(x -> {
                stringList.add(String.valueOf(x.getProcessId()));
            });
            log.info("回收的流程ID:" + stringList);
            sysLogServiceFeign.save(new SysLogEntity().setCreateTime(new Date()).setOperation("即将被回收商机流程").setMethod("回收任务")
                    .setUsername("系统").setParams(JSON.toJSONString(list)).setTime(System.currentTimeMillis() - beginTime)
                    .setIp(InetAddress.getLocalHost().getHostAddress()));
            //设置流程过期
            baseDao.disableProcessActiveBatch(StringUtils.listToString(stringList));
            //customerId去重
            List<TransferProcessEntity> customerList = list.stream().filter(StringUtils.distinctByKey(TransferProcessEntity::getCustomerId)).collect(Collectors.toList());
            List<String> customerIdList = new ArrayList<>();
            List<TransferProcessEntity> saveProcessList = new ArrayList<>();
            customerList.forEach(c -> {
                customerIdList.add(String.valueOf(c.getCustomerId()));
                saveProcessList.add(new TransferProcessEntity().setDeptId(c.getDeptId()).setDeptName(c.getDeptName()).setCustomerId(c.getCustomerId())
                        .setCreateUserId(SystemId.User.NO_CREATE_ID.getValue()).setCreateUserName("/").setMemo("回收生成流程"));
            });
            //新增流程记录(批量)
            baseDao.saveBatch(saveProcessList);
            //更新客户表user_id为null（批量）
            transferCustomerService.returnToCommonBatch(StringUtils.listToString(customerIdList), SystemId.User.NO_CREATE_ID.getValue());
            log.info("回收的客户ID:" + customerIdList);
            //记录系统回收日志
            sysLogServiceFeign.save(new SysLogEntity().setCreateTime(new Date()).setOperation("成功被回收商机流程").setMethod("回收任务")
                    .setUsername("系统").setParams(JSON.toJSONString(saveProcessList)).setTime(System.currentTimeMillis() - beginTime)
                    .setIp(InetAddress.getLocalHost().getHostAddress()));
        } else {
            //记录系统回收日志
            sysLogServiceFeign.save(new SysLogEntity().setCreateTime(new Date()).setOperation("不存在需要回收的商机").setMethod("回收任务")
                    .setUsername("系统").setTime(System.currentTimeMillis() - beginTime).setIp(InetAddress.getLocalHost().getHostAddress()));
        }
    }

    private List<TransferProcessEntity> findListIsActive(String time) {
        return baseDao.findListIsActive(time);
    }
}
