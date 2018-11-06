package com.hqjy.mustang.transfer.crawler.service.impl;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.crawler.dao.TransferEmailDao;
import com.hqjy.mustang.transfer.crawler.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;
import com.hqjy.mustang.transfer.crawler.service.TrasferEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isGeneralSeat;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isSuperAdmin;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:17
 */
@Service
@Slf4j
public class TrasferEmailServiceImpl extends BaseServiceImpl<TransferEmailDao, TransferEmailEntity, Long> implements TrasferEmailService {

    private SysDeptServiceFeign sysDeptServiceFeign;
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Autowired
    public void setSysDeptServiceFeign(SysDeptServiceFeign sysDeptServiceFeign) {
        this.sysDeptServiceFeign = sysDeptServiceFeign;
    }
    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }
    /**
     * 查询所有有效email配置
     */
    @Override
    public List<TransferEmailEntity> findAllValid() {
        return baseDao.findAllValid();
    }

    /**
     * 修改简历发送状态
     */
    @Override
    public int updateStatus(Long id, int status) {
        return baseDao.updateStatus(id, status);
    }

    @Override
    public int save(TransferEmailEntity entity) {
        entity.setCreateUserId(getUserId());
        entity.setCreateUserName(ShiroUtils.getUserName());
        return super.save(entity);
    }

    @Override
    public int update(TransferEmailEntity entity) {
        entity.setUpdateUserId(getUserId());
        entity.setUpdateUserName(ShiroUtils.getUserName());
        return super.update(entity);
    }

    @Override
    public List<TransferEmailEntity> findPage(PageQuery pageQuery) {
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<String> ids = new ArrayList<>();
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);
            allDeptUnderDeptId.forEach(x -> {
                String deptIds = String.valueOf(x);
                ids.add(deptIds);
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        if (isGeneralSeat()) {
            pageQuery.put("userId", getUserId());
        }
        if (isSuperAdmin()) {
            log.debug("用户角色是超级管理员：" + isSuperAdmin());
            return super.findPage(pageQuery);
        }
        //如果没有刷选部门过滤条件
        if (null == deptId) {
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
            List<String> deptIds = new ArrayList<>();
            userAllDeptId.forEach(x -> {
                String ids = String.valueOf(x);
                deptIds.add(ids);
            });
            pageQuery.put("deptIds", StringUtils.listToString(deptIds));
        }
        return super.findPage(pageQuery);
    }
}
