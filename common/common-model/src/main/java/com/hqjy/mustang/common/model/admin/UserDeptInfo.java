package com.hqjy.mustang.common.model.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xyq
 * @date create on 2018/10/19
 * @apiNote
 */
@Data
@Accessors(chain = true)
public class UserDeptInfo {

    /**
     * 用户编号
     **/
    private Long userId;

    /**
     * 用户名
     **/
    private String userName;


    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
}
