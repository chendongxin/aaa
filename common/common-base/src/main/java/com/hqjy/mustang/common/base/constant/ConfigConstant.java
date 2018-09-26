package com.hqjy.mustang.common.base.constant;

/**
 * @author XYQ 2018年9月12日17:32:07
 * 系统参数相关
 */
public interface ConfigConstant {
    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * websocket地址
     */
    String WEBSOCKET_HOST = "WEBSOCKET_HOST";

    /**
     * 分配算法类型，0 只根据权重 ；1 根据权重和排班
     */
    String TRANSFER_ALLOT_ALGORITHM = "TRANSFER_ALLOT_ALGORITHM";

    /**
     * 没有指定商机归属，或则分配部门不存在，指定分配到此部门
     */
    String TRANSFER_ALLOT_DEFAULT_DEPTID = "TRANSFER_ALLOT_DEFAULT_DEPTID";

    /**
     * Transfer首次分配超时时间 单位天
     */
    String TRANSFER_ALLOT_FIRST_TIMEOUT = "TRANSFER_ALLOT_FIRST_TIMEOUT";

    /**
     * Transfer二次分配超时间 单位天
     */
    String TRANSFER_ALLOT_REPEAT_TIMEOUT = "TRANSFER_ALLOT_REPEAT_TIMEOUT";

}
