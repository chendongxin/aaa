package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysUserExtendDao;
import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 用户扩展信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:06
 */
@Service
public class SysUserExtendServiceImpl extends BaseServiceImpl<SysUserExtendDao, SysUserExtendEntity, Long> implements SysUserExtendService {

    /**
     * 根据用户Id查询,HSS 2018-07-05
     */
    @Override
    public SysUserExtendEntity findByUserId(Long userId) {
        return baseDao.findByUserId(userId);
    }

    /**
     *分页查询
     */
    @Override
    public List<SysUserExtendEntity> findPage(PageQuery pageQuery) {
        List<SysUserExtendEntity> userExtendList = super.findPage(pageQuery);
        return userExtendList;
    }

    /**
     * 保存人员映射
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysUserExtendEntity userExtend){
        userExtend.setCreateId(ShiroUtils.getUserId());
        int count = baseDao.save(userExtend);
        return count;
    }

    /**
     * 人员映射关系修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserExtendEntity userExtend){
        return baseDao.update(userExtend);
    }

}
