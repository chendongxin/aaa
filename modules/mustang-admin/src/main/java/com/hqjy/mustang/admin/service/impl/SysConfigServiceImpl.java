package com.hqjy.mustang.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.admin.dao.SysConfigDao;
import com.hqjy.mustang.admin.dao.SysConfigInfoDao;
import com.hqjy.mustang.admin.model.entity.SysConfigEntity;
import com.hqjy.mustang.admin.model.entity.SysConfigInfoEntity;
import com.hqjy.mustang.admin.service.SysConfigService;
import com.hqjy.mustang.admin.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 系统配置
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:37
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfigEntity, Long> implements SysConfigService {

    /**
     * 缓存时间10分钟 =  10 * 60 秒
     */
    private static final long CONFIG_EXPIRE = 10 * 60;

    @Autowired
    private SysConfigInfoDao sysConfigInfoDao;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 配置目录分页查询
     */
    @Override
    public List<SysConfigEntity> findPage(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return baseDao.findPage(pageQuery);
    }


    /**
     * 保存配置目录
     */
    @Override
    public int save(SysConfigEntity config) {
        SysConfigEntity newConfig = PojoConvertUtil.convert(config, SysConfigEntity.class);
        newConfig.setCreateId(ShiroUtils.getUserId());
        newConfig.setStatus(Constant.Status.NORMAL.getValue());
        return baseDao.save(newConfig);
    }

    /**
     * 修改配置目录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysConfigEntity config) {
        SysConfigEntity newConfig = PojoConvertUtil.convert(config, SysConfigEntity.class);
        newConfig.setUpdateId(ShiroUtils.getUserId());
        SysConfigEntity oldConfig = baseDao.findOne(config.getConfigId());

        //修改CODE,配置属性同步修改
        if (!oldConfig.getCode().equals(newConfig.getCode())) {
            sysConfigInfoDao.updateCode(oldConfig.getCode(), newConfig.getCode());
        }

        //清除缓存
        redisUtils.delete(RedisKeys.Config.key(config.getCode()));

        return baseDao.update(newConfig);
    }

    /**
     * 删除配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        SysConfigEntity oldConfig = baseDao.findOne(id);

        //检查是否有具体配置，有具体配置不允许删除
        SysConfigInfoEntity config = new SysConfigInfoEntity();
        config.setCode(oldConfig.getCode());
        if (sysConfigInfoDao.count(config) > 0) {
            throw new RRException("存在配置属性，不允许删除配置");
        }

        int count = baseDao.delete(id);
        if (count > 0) {
            //清除缓存
            redisUtils.delete(RedisKeys.Config.key(config.getCode()));
        }
        return count;
    }

    /**
     * 根据配置code获取配置值列表
     */
    @Override
    public List<SysConfigInfoEntity> getInfoListByCode(String code) {
        List<SysConfigInfoEntity> list = sysConfigInfoDao.findInfoListByCode(code);
        SysConfigEntity sysConfigEntity = baseDao.findOneByCode(code);
        if (sysConfigEntity != null) {
            // 对字符串进行处理，去掉前后引号
            if (Constant.DataType.STRING.equals(sysConfigEntity.getType())) {
                list.forEach(configInfo -> configInfo.setValue(StringUtils.cut(configInfo.getValue(), "\"", "\"")));
            }
        }
        return list;
    }

    /**
     * 保存配置属性
     */
    @Override
    public int saveInfo(SysConfigInfoEntity config) {
        config.setCreateId(ShiroUtils.getUserId());
        SysConfigEntity configEntity = baseDao.findOneByCode(config.getCode());
        if (configEntity != null && StringUtils.isNotEmpty(config.getValue())) {
            if (Constant.DataType.STRING.equals(configEntity.getType())) {
                config.setValue(JsonUtil.toJson(config.getValue()));
            }
            return sysConfigInfoDao.save(config);
        }
        return 0;
    }

    /**
     * 修改配置属性
     */
    @Override
    public int updateInfo(SysConfigInfoEntity config) {
        config.setUpdateId(ShiroUtils.getUserId());
        int count = 0;
        SysConfigEntity configEntity = baseDao.findOneByCode(config.getCode());
        if (configEntity != null && StringUtils.isNotEmpty(config.getValue())) {
            if (Constant.DataType.STRING.equals(configEntity.getType())) {
                config.setValue(JsonUtil.toJson(config.getValue()));
            }
            count = sysConfigInfoDao.update(config);
            if (count > 0) {
                //清除缓存
                redisUtils.delete(RedisKeys.Config.key(config.getCode()));
            }
        }
        return count;
    }

    /**
     * 删除配置属性
     */
    @Override
    public int deleteBatchInfo(Long[] ids) {
        int count = 0;
        for (Long id : ids) {
            //清除缓存
            Optional<SysConfigInfoEntity> config = Optional.ofNullable(sysConfigInfoDao.findOne(id));
            if (config.isPresent()) {
                redisUtils.delete(RedisKeys.Config.key(config.get().getCode()));
                count = count + sysConfigInfoDao.delete(id);
            }
        }
        return count;
    }

    /**
     * 根据code获取系统配置信息
     */
    @Override
    public String getConfigByCode(String code) {
        SysConfigEntity config = baseDao.getConfig(code);
        if (config != null) {
            // 字符串去掉前后引号
            if (Constant.DataType.STRING.equals(config.getType())) {
                String value = config.getValue();
                config.setValue(value.substring(1, value.length() - 1));
            }
            return config.getValue();
        }
        return null;
    }

    /**
     * 根据code获取系统配置信息
     */
    @Override
    public String getConfig(String code) {
        //缓存中存在直接返回
        String strValue = redisUtils.get(RedisKeys.Config.key(code));
        if (strValue != null) {
            return strValue;
        }
        strValue = getConfigByCode(code);
        if (strValue != null) {
            redisUtils.set(RedisKeys.Config.key(code), strValue, CONFIG_EXPIRE);
        }
        return strValue;
    }

    /**
     * 根据code获取系统配置信息
     */
    @Override
    public <T> T getConfig(String code, Class<T> clazz) {
        //缓存中存在直接返回
        Optional<T> object = Optional.ofNullable(redisUtils.get(RedisKeys.Config.key(code), clazz));
        if (object.isPresent()) {
            return object.get();
        }
        //不存在从数据库拿
        Optional<String> configStr = Optional.ofNullable(getConfigByCode(code));
        if (configStr.isPresent()) {
            //return configStr.map(s -> JsonUtil.fromJson(s, clazz)).orElse(null);
            T config = JsonUtil.fromJson(configStr.get(), clazz);
            redisUtils.set(RedisKeys.Config.key(code), config, CONFIG_EXPIRE);
            return config;
        }
        return null;
    }

}
