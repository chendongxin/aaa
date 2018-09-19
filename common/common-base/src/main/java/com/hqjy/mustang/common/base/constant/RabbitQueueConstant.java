package com.hqjy.mustang.common.base.constant;

/**
 * RabbitMq 队列常量
 */
public interface RabbitQueueConstant {

    /**
     * 系统操作日志记录
     */
    String SYS_LOG_QUEUE = "SYS_LOG_QUEUE";

    /**
     * 业务消息队列消费日志
     */
    String LOG_MESSAGE_QUEUE = "LOG_MESSAGE_QUEUE";

    /**
     * 商机业务消息队列
     */
    String MUSTANG_TRANSFER_QUEUE = "MUSTANG_TRANSFER_QUEUE";

}
