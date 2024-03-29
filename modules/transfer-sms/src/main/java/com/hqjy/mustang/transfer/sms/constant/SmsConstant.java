package com.hqjy.mustang.transfer.sms.constant;


public class SmsConstant {

    /**
     * 短信发送状态
     */
    public enum SendStatus {
        /**
         * 等待发送
         */
        AWAIT("等待发送", 0),
        /**
         * 提交发送
         */
        SUBMIT("提交发送", 1),
        /**
         * 发送成功
         */
        SUCCESS("发送成功", 2),
        /**
         * 发送失败
         */
        FAILURE("发送失败", -1),
        /**
         * 发送失败
         */
        ILLEGAL("内容不合法", -502);

        private String value;
        private Integer code;

        SendStatus(String value, Integer code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }

        public SendStatus handleSendStatusName(Integer code) {
            for (SmsConstant.SendStatus e : SmsConstant.SendStatus.values()) {
                if (code.equals(e.getCode())) {
                    return e;
                }
            }
            return SmsConstant.SendStatus.FAILURE;
        }
    }

    /**
     * 短信回复状态
     */
    public enum SendReply {

        /**
         * 未发送
         */
        UNSENT(0),

        /**
         * 已发送
         */
        SENT(1);

        private Integer code;

        SendReply(Integer code) {
            this.code = code;
        }


        public Integer getCode() {
            return code;
        }

    }
}
