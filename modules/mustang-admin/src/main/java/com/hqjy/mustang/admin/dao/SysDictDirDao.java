package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysDictDirEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_dict_dir 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/04/19 16:14
 */
@Mapper
public interface SysDictDirDao extends BaseDao<SysDictDirEntity, Long> {
    /**
     * 根据编码查询数据字典目录
     */
    SysDictDirEntity findByCode(String code);
}