package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysUserProDao;
import com.hqjy.mustang.admin.model.entity.SysUserProEntity;
import com.hqjy.mustang.admin.service.SysUserProService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysUserProService")
public class SysUserProServiceImpl extends BaseServiceImpl<SysUserProDao, SysUserProEntity, Integer> implements SysUserProService {

    /**
     * 保存用户赛道关系
     */
    @Override
    public int save(Long userId, List<SysUserProEntity> userProList) {
        //先删除用户赛道关系
        int count = baseDao.deleteByUserId(userId);
        if (userProList.size() == 0) {
            return count;
        }
        //保存用户与赛道关系
        return baseSave(userId, userProList);
    }

    private int baseSave(Long userId, List<SysUserProEntity> userProList) {
        int count = 0;
        for (SysUserProEntity pro : userProList) {
            SysUserProEntity userProEntity = new SysUserProEntity();
            userProEntity.setUserId(userId);
            userProEntity.setProId(pro.getProId());
            userProEntity.setCreateUserId(ShiroUtils.getUserId());
            count += baseDao.save(userProEntity);
        }
        return count;
    }

    /**
     * 查询用户赛道列表,包含赛道信息
     */
    @Override
    public List<SysUserProEntity> getUserProInfoList(Long userId) {
        return baseDao.findUserProInfoList(userId);
    }
}
