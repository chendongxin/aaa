package com.hqjy.mustang.allot.constant;

/**
 * 分配业务状态码
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:25
 */
public enum AllotCode {
    MQ_REDELIVERED(1000, "消息重试"),
    MQ_TYPE_ERROR(1001, "无法识别消息类型"),
    MQ_SOURCE_ERROR(1002, "无法识别商机来源"),
    BIZ_CONTACT_ERROR(1003, "商机没有任何联系方式"),
    BIZ_CONCURRENCY(1004, "并发商机,不进行处理"),
    BIZ_INVALID(1005, "无效商机,不进行处理");
    private final int code;
    private final String msg;

    AllotCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}