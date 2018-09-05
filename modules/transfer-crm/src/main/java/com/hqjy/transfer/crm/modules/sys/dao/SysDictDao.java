package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_dict 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/19 14:31
 */
@Mapper
public interface SysDictDao extends BaseDao<SysDictEntity, Long> {
    /**
     * 修改配置属性编码
     */
    int updateCode(@Param("oldCode") String oldCode, @Param("newCode") String newCode);

    /**
     * 获取数据字典项
     */
    List<SysDictEntity> getDictByCode(String code);
}