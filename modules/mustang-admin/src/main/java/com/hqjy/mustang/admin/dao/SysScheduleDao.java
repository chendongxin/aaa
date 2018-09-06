package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.dto.SysScheduleDto;
import com.hqjy.mustang.admin.model.entity.SysScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * sys_schedule 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/23 14:15
 */
@Mapper
public interface SysScheduleDao extends BaseDao<SysScheduleEntity, Long> {


    /**
     * 根据多个参数删除排班
     *
     * @param params 参数
     */
    int deleteSchedule(Map<String, Object> params);

    /**
     * 根据多个参数删除排班
     *
     * @param scheduleQuery 参数
     */
    int deleteSchedule(SysScheduleDto scheduleQuery);


    /**
     * 根据条件获取已排班的用户
     *
     * @param params deptId,每周第一天日期monday
     * @return 根据条件获取已排班的用户
     */
    List<Long> getUserIdBySchedule(Map<String, Object> params);

    /**
     * 删除用户所有排班
     */
    int deleteUserAllSchedule(@Param("userId") Long userId);

    /**
     * 用户部门变更，删除用户原来部门下的排班
     */
    int deleteUserOldDeptIdSchedule(@Param("userId") Long userId);
}
