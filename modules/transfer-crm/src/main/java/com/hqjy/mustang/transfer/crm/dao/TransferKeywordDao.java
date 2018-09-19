package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_keyword 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferKeywordDao extends BaseDao<TransferKeywordEntity, Integer> {

    /**
     * 获取所有关键词列表
     */
    List<TransferKeywordEntity> getAllKeywordList();

    /**
     * 获取父ID下对应关键词
     */
    List<TransferKeywordEntity> findByParentId(Integer parentId);

    /**
     * 通过名称查询一条关键词
     */
    TransferKeywordEntity findOneByName(String name);
}