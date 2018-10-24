package com.hqjy.mustang.transfer.sms.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.sms.constant.SmsConstant;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsReplyDao;
import com.hqjy.mustang.transfer.sms.fegin.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsReplyEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsReplyService;
import com.hqjy.mustang.transfer.sms.service.TransferSmsService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.*;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 11:36
 */
@Service
public class TransferSmsReplyServiceImpl extends BaseServiceImpl<TransferSmsReplyDao, TransferSmsReplyEntity, Long> implements TransferSmsReplyService {

    private final TransferSmsService transferSmsService;

    @Autowired
    public TransferSmsReplyServiceImpl(TransferSmsService transferSmsService) {
        this.transferSmsService = transferSmsService;
    }

    @Autowired
    private SysDeptServiceFeign sysDeptServiceFeign;


    /**
     * 回复短信
     */
    @Override
    public int smsReply(Long id, TransferSmsEntity smsEntity) {
        int count = 0;
        TransferSmsReplyEntity smsReplyEntity = baseDao.findOne(id);
        if (smsReplyEntity != null) {
            smsEntity.setDeptId(smsReplyEntity.getDeptId());
            smsEntity.setDeptName(smsReplyEntity.getDeptName());
            smsEntity.setPhone(smsReplyEntity.getPhone());
            // 保存发送短信
            count = transferSmsService.save(smsEntity);
            if (count > 0) {
                System.out.println("smsEntity.getId()=>" + smsEntity.getId());
                Long[] smsIds = {smsEntity.getId()};
                // 执行发送短信
                transferSmsService.smsSend(smsIds);
                // 修改回复短信状态
                smsReplyEntity.setStatus(SmsConstant.SendReply.SENT.getCode());
                baseDao.update(smsReplyEntity);
            }
        }
        return count;
    }

    /**
     * 获取短信回复信息
     */
    @Override
    public List<TransferSmsEntity> replyList(Long id, PageQuery pageQuery) {
        TransferSmsReplyEntity transferSmsReplyEntity = baseDao.findOne(id);
        return Optional.ofNullable(transferSmsReplyEntity).map(t -> {
            TransferSmsEntity smsEntity = new TransferSmsEntity();
            smsEntity.setDeptId(transferSmsReplyEntity.getDeptId());
            smsEntity.setPhone(transferSmsReplyEntity.getPhone());
            PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
            return transferSmsService.findList(smsEntity);
        }).orElseGet(ArrayList::new);
    }

    @Override
    public List<TransferSmsReplyEntity> findPage(PageQuery pageQuery) {
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);
            List<String> ids = new ArrayList<>();
            allDeptUnderDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        //如果没有刷选部门过滤条件
        if (null == deptId) {
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysDeptServiceFeign.getUserDeptIdList(getUserId());
            List<String> deptIds = new ArrayList<>();
            userAllDeptId.forEach(x -> {
                deptIds.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(deptIds));
        }
        return super.findPage(pageQuery);
    }
}
