package com.hqjy.mustang.allot.service;

import com.hqjy.mustang.allot.feign.SysConfigApiService;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * 商机消息消费抽象
 *
 * @author : heshuangshuang
 * @date : 2018/9/14 15:40
 */
public abstract class AbstractHandleService<T extends Serializable> {

    @Autowired
    private SysConfigApiService sysConfigApiService;

    /**
     * 预处理,保存用户基本信息和联系方式
     * 通过联系方式判断重单情况
     */
    protected abstract ContactSaveResultDTO pretreatment(T customer);


    /**
     * 分配,设置用户ID和部门ID
     */
    protected abstract void allot(ContactSaveResultDTO resultDTO, T customer);

    /**
     * 重单商机保存处理
     */
    protected abstract void repeatSave(ContactSaveResultDTO resultDTO, T customer);

    /**
     * 保存到数据库
     */
    protected abstract Long saveProcess(ContactSaveResultDTO resultDTO, T customerEntity);

    /**
     * 发送消息
     */
    protected abstract void sendMessage(ContactSaveResultDTO resultDTO, T customerEntity);

    /**
     * 分配完成后续操作，保存重单商机，更新客户表,异步保存到NC
     */
    protected abstract void finalTreatment(Long processId, T customer);

    /**
     * 处理超时时间
     */
    protected Date processTimeout(String code, int defDay) {
        // 超时的天数
        int dayCount = Optional.ofNullable(sysConfigApiService.getConfig(code)).map(Integer::valueOf).orElse(defDay);
        return Date.from(LocalDateTime.now().plusDays(dayCount).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 开始处理
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean start(T customer) {
        // 预处理
        ContactSaveResultDTO saveResultDTO = pretreatment(customer);
        // 分配，根据部门获取人员
        allot(saveResultDTO, customer);
        // 保存流程记录
        Long processId = saveProcess(saveResultDTO, customer);
        //发送webSocket消息,需要分别处理，首次和二次
        if (processId != null) {
            sendMessage(saveResultDTO, customer);
        }
        // 重单商机保存处理
        repeatSave(saveResultDTO, customer);
        // 最后处理
        finalTreatment(processId, customer);
        return true;
    }

}
