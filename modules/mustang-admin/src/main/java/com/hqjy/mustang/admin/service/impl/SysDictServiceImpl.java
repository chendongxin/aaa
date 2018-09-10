package com.hqjy.mustang.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.admin.dao.SysDictDao;
import com.hqjy.mustang.admin.dao.SysDictDirDao;
import com.hqjy.mustang.admin.model.dto.DictDTO;
import com.hqjy.mustang.admin.model.entity.SysDictDirEntity;
import com.hqjy.mustang.admin.model.entity.SysDictEntity;
import com.hqjy.mustang.admin.service.SysDictService;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典管理
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:37
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictDao, SysDictEntity, Long> implements SysDictService {

    @Autowired
    private SysDictDirDao sysDictDirDao;

    /**
     * 查询字典目录列表
     */
    @Override
    public List<SysDictDirEntity> getConfigDirList() {
        return sysDictDirDao.findAllList();
    }

    /**
     * 字典目录分页查询
     */
    @Override
    public List<SysDictDirEntity> findPageDir(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return sysDictDirDao.findPage(pageQuery);
    }

    /**
     * 字典目录信息
     */
    @Override
    public SysDictDirEntity findOneDir(Long id) {
        return sysDictDirDao.findOne(id);
    }

    /**
     * 保存字典目录
     */
    @Override
    public int saveConfigDir(SysDictDirEntity configDir) {
        SysDictDirEntity newConfigDir = PojoConvertUtil.convert(configDir, SysDictDirEntity.class);
        newConfigDir.setCreateId(ShiroUtils.getUserId());
        return sysDictDirDao.save(newConfigDir);
    }

    /**
     * 修改字典目录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateConfigDir(SysDictDirEntity configDir) {
        SysDictDirEntity newConfigDir = PojoConvertUtil.convert(configDir, SysDictDirEntity.class);
        newConfigDir.setUpdateId(ShiroUtils.getUserId());
        SysDictDirEntity oldConfigDir = sysDictDirDao.findOne(configDir.getId());

        //检查是否有具体字典，有具体字典不允许修改类型
        if (!oldConfigDir.getType().equals(newConfigDir.getType())) {
            SysDictEntity config = new SysDictEntity();
            config.setCode(oldConfigDir.getCode());
            if (baseDao.count(config) > 0) {
                throw new RRException("存在字典属性，不允许修改字典类型");
            }
        }

        //修改CODE,字典属性同步修改
        if (!oldConfigDir.getCode().equals(newConfigDir.getCode())) {
            baseDao.updateCode(oldConfigDir.getCode(), newConfigDir.getCode());
        }

        return sysDictDirDao.update(newConfigDir);
    }

    /**
     * 删除字典目录
     */
    @Override
    public int deleteConfigDir(Long id) {
        SysDictDirEntity oldConfigDir = sysDictDirDao.findOne(id);
        //检查是否有具体字典，有具体字典不允许删除
        SysDictEntity config = new SysDictEntity();
        config.setCode(oldConfigDir.getCode());
        if (baseDao.count(config) > 0) {
            throw new RRException("存在字典属性，不允许删除");
        }

        return sysDictDirDao.delete(id);
    }

    /**
     * 获取数据字典项
     */
    @Override
    public List<DictDTO> getDictByCode(String code) {
        //查询数据类性
        SysDictDirEntity dirEntity = sysDictDirDao.findByCode(code);
        List<SysDictEntity> list = baseDao.getDictByCode(code);
        List<DictDTO> dictList = new ArrayList<>();
        list.forEach(value -> {
            DictDTO dict = new DictDTO();
            dict.setLabel(value.getLabel());
            if (Constant.DataType.INTEGER.equals(dirEntity.getType())) {
                //整型
                dict.setValue(Integer.valueOf(value.getValue()));
            } else if (Constant.DataType.DOUBLE.equals(dirEntity.getType())) {
                //浮点型
                dict.setValue(Double.valueOf(value.getValue()));
            } else if (Constant.DataType.BOOLEAN.equals(dirEntity.getType())) {
                //布尔型
                dict.setValue(Boolean.valueOf(value.getValue()));
            } else {
                dict.setValue(value.getValue());
            }
            dictList.add(dict);
        });
        return dictList;
    }

    /**
     * 保存字典
     */
    @Override
    public int save(SysDictEntity dict) {
        SysDictEntity newDict = PojoConvertUtil.convert(dict, SysDictEntity.class);
        newDict.setCreateId(ShiroUtils.getUserId());
        return super.save(newDict);
    }

    /**
     * 修改字典
     */
    @Override
    public int update(SysDictEntity dict) {
        SysDictEntity newDict = PojoConvertUtil.convert(dict, SysDictEntity.class);
        newDict.setUpdateId(ShiroUtils.getUserId());
        return super.update(newDict);
    }
}
