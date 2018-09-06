package com.hqjy.mustang.common.base.base;

/**
 * @author : heshuangshuang
 * @date : 2018/1/19 15:14
 */

import com.hqjy.mustang.common.base.utils.PageQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID extends Serializable> {

    /**
     * 增加
     */
    int save(T entity);

    int save(Map<String, Object> map);

    int saveBatch(List<T> list);

    /**
     * 更新
     */
    int update(T entity);

    int update(Map<String, Object> map);

    /**
     * 删除
     */
    int delete(ID id);

    int delete(Map<String, Object> map);

    int deleteBatch(ID[] id);

    int deleteBatch(Object id);

    int deleteList(List<T> entity);

    /**
     * 查询
     */
    T findOne(ID id);

    List<T> findList(T entity);

    List<T> findList(Map<String, Object> map);

    List<T> findPage(PageQuery pageQuery);

    List<T> findAllList();

    /**
     * 实体属性的记录数量
     */
    int count(T entity);

    /**
     * 实体是否存在
     */
    boolean exsit(T entity);


}
