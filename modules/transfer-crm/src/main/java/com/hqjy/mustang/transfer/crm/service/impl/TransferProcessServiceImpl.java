package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferProcessDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferProcessServiceImpl extends BaseServiceImpl<TransferProcessDao, TransferProcessEntity, Long> implements TransferProcessService {

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
}
