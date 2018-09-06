package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.admin.model.dto.MessageTypeCountDTO;
import com.hqjy.mustang.admin.model.entity.SysMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_message 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/20 14:49
 */
@Mapper
public interface SysMessageDao extends BaseDao<SysMessageEntity, Long> {
    /**
     * 用户查询消息列表
     */
    List<SysMessageEntity> findMessageListPage(PageQuery pageQuery);

    /**
     * 标记已读
     */
    int updateReadByMsgId(@Param("userId") Long userId, @Param("msgId") Long msgId);

    /**
     * 标记用户类型消息已读
     */
    int updateReadByType(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 用户指定消息
     */
    int deleteByMsgId(@Param("userId") Long userId, @Param("msgId") Long msgId);

    /**
     * 清空用户类型消息
     */
    int deleteByType(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 统计消息数量
     */
    List<MessageTypeCountDTO> countInfo(@Param("userId") Long userId);

    /**
     * 统计消息总数量
     */
    int totalCount(@Param("userId") Long userId);
}