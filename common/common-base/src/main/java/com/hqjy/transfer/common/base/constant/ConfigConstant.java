package com.hqjy.transfer.common.base.constant;

/**
 * 系统参数相关Key
 */
public interface ConfigConstant {
    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 没有指定商机归属，或则分配部门不存在，指定分配到此部门
     */
    String ALLOT_DEFAULT_DEPTID = "ALLOT_DEFAULT_DEPTID";

    /**
     * 首次分配超时时间 单位天
     */
    String ALLOT_FIRST_TIMEOUT = "ALLOT_FIRST_TIMEOUT";

    /**
     * 二次分配超时间 单位天
     */
    String ALLOT_REPEAT_TIMEOUT = "ALLOT_REPEAT_TIMEOUT";

    /**
     * 延时消费 时间 秒
     */
    String ALLOT_DELAY_SEC = "ALLOT_DELAY_SEC";

    /**
     * api验证摘要密码
     */
    String API_SECRET = "API_SECRET";

    /**
     * 小能主机地址
     */
    String SELF_EXAM_HOST = "SELF_EXAM_HOST";

    /**
     * websocket地址
     */
    String WEBSOCKET_HOST = "WEBSOCKET_HOST";

    /**
     * 天润接口授权参数
     */
    String TINET_CONFIG = "TINET_CONFIG";

    /**
     * 公海领取商机上限
     */
    String BIZ_CUSTOMER_OPPORTUNITY = "BIZ_CUSTOMER_OPPORTUNITY";

    /**
     * 导入客户上限
     */
    String BIZ_IMPORT_LIMIT = "BIZ_IMPORT_LIMIT";

    /**
     * 导出客户上限
     */
    String BIZ_EXPORT_LIMIT = "BIZ_EXPORT_LIMIT";

    /**
     * 邮件发送地址
     */
    String MAIL_SEND_TO = "MAIL_SEND_TO";
}
