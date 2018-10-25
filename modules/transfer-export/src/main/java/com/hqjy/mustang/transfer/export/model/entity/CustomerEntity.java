package com.hqjy.mustang.transfer.export.model.entity;

import lombok.Data;

/**
 * @author xyq
 * @date create on 2018/10/11
 * @apiNote
 */
@Data
public class CustomerEntity {

    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 数量
     */
    private int num;

    /**
     * 通话时长
     */
    private String duration;
}
