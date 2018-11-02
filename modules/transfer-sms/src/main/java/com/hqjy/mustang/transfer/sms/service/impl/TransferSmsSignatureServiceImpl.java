package com.hqjy.mustang.transfer.sms.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsSignatureDao;
import com.hqjy.mustang.transfer.sms.fegin.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsReplyEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsTemplateEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsSignatureService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:47
 */
@Service
public class TransferSmsSignatureServiceImpl extends BaseServiceImpl<TransferSmsSignatureDao, TransferSmsSignatureEntity, Long> implements TransferSmsSignatureService {

    @Autowired
    private SysDeptServiceFeign sysDeptServiceFeign;
    /**
     * 根据部门编号获取签名列表
     */
    @Override
    public List<TransferSmsSignatureEntity> getListByDeptId(Long deptId) {
        return baseDao.getListByDeptId(deptId);
    }

    @Override
    public int save(TransferSmsSignatureEntity entity) {
        entity.setCreateUserId(ShiroUtils.getUserId());
        entity.setCreateUserName(ShiroUtils.getUserName());
        return super.save(entity);
    }

    @Override
    public int update(TransferSmsSignatureEntity entity) {
        entity.setUpdateUserId(ShiroUtils.getUserId());
        entity.setUpdateUserName(ShiroUtils.getUserName());
        return super.update(entity);
    }

    @Override
    public List<TransferSmsSignatureEntity> findPage(PageQuery pageQuery) {
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
