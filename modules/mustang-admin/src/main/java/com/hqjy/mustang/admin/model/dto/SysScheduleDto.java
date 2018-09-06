package com.hqjy.mustang.admin.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xieyuqing
 * @ description 排班列表查询参数
 * @ date create in 2018/5/25 14:59
 */
@Data
@Accessors(chain = true)
public class SysScheduleDto {

    /**
     * 部门树节点deptId值
     */
    private Long treeValue;

    /**
     * 列表部门deptId
     */
    private Long deptId;


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 周一天
     */
    private String monday;


    /**
     * 周日天
     */
    private String sunday;
}
