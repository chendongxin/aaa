package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysUserExtendDao;
import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        userExtend.setTqPw(new Sha256Hash(userExtend.getTqPw(),salt).toHex());
        int count = baseDao.save(userExtend);
        return count;
    }

    /**
     * 人员映射关系修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserExtendEntity userExtend){
        if(StringUtils.isBlank(userExtend.getTqPw())){
            userExtend.setTqPw(null);
        }else{
            //验证密码字段；由于该密码为4-8位，因此我们单独写校验方法；
            if (StringUtils.isNotBlank(userExtend.getTqPw())) {
                String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(userExtend.getTqPw());
                if (!matcher.find()) {
                    throw new RRException("密码4-8位，必须包含数字和字母");
                }
            }
            String salt =  RandomStringUtils.randomAlphanumeric(20);
            userExtend.setTqPw(new Sha256Hash(userExtend.getTqPw(),salt).toHex());
        }
        return baseDao.update(userExtend);
    }

}
