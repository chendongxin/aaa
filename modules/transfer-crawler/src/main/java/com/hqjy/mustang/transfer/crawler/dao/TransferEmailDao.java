package com.hqjy.mustang.transfer.crawler.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_email 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/12 15:13
 */
@Mapper
public interface TransferEmailDao extends BaseDao<TransferEmailEntity, Long> {

    /**
     * 查询素有有效email配置
     */
    List<TransferEmailEntity> findAllValid();

    /**
     * 修改简历发送状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}