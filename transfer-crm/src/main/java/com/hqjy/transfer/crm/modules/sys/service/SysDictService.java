package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.common.base.utils.PageQuery;
import com.hqjy.transfer.crm.modules.sys.model.dto.DictDTO;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDictDirEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDictEntity;

import java.util.List;

/**
 * 数据字典配置
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:30
 */
public interface SysDictService extends BaseService<SysDictEntity, Long> {

    /**
     * 查询配置字典列表
     */
    List<SysDictDirEntity> getConfigDirList();

    /**
     * 配置字典分页查询
     */
    List<SysDictDirEntity> findPageDir(PageQuery pageQuery);

    /**
     * 配置字典信息
     */
    SysDictDirEntity findOneDir(Long id);

    /**
     * 保存配置字典
     */
    int saveConfigDir(SysDictDirEntity configDir);

    /**
     * 修改配置字典
     */
    int updateConfigDir(SysDictDirEntity configDir);

    /**
     * 删除配置字典
     */
    int deleteConfigDir(Long id);

    /**
     * 获取数据字典项
     */
    List<DictDTO> getDictByCode(String code);
}
