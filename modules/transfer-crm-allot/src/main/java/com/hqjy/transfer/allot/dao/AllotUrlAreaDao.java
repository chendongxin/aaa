package com.hqjy.transfer.allot.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import org.mapstruct.Mapper;
import com.hqjy.transfer.allot.model.entity.AllotUrlAreaEntity;

/**
 * cust_url_area 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/19 09:54
 */
@Mapper
public interface AllotUrlAreaDao extends BaseDao<AllotUrlAreaEntity, Long> {
    /**
     * 通过url查询一个映射信息
     */
    AllotUrlAreaEntity findOneByUrl(String url);
}