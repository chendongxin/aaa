package com.hqjy.mustang.common.base.constant;


/**
 * @author XYQ
 * 常量
 */
public class Constant {
    /**
     * 请求头中 JWT 的key
     */
    public static final String AUTHOR_PARAM = "token";

    /**
     * JWT 签名
     */
    public static final String JWT_SIGN_KEY = "hqjy.mustang";

    /**
     * JWT token 用户名
     */
    public static final String JWT_TOKEN_USERNAME = "jtu";

    /**
     * JWT token 用户编号
     */
    public static final String JWT_TOKEN_USERID = "uid";

    /**
     * JWT_编号
     */
    public static final String JWT_ID = "jti";

    /**
     * 服务内部调用前缀
     */
    public static final String API_PATH = "/api/private/";
    public static final String API_PATH_ANON = API_PATH + "**";

    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String RESULT = "result";


    /**
     * 通用状态
     */
    public enum Status {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 禁用
         */
        DISABLE(1);

        private Integer value;

        Status(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value.equals(this.value);
        }
    }

    /**
     * 数据类型
     * 0：字符串 1：整型  2：浮点型  3：布尔  4：json对象
     */
    public enum DataType {
        /**
         * 字符串
         */
        STRING(0),
        /**
         * 整型
         */
        INTEGER(1),
        /**
         * 浮点型
         */
        DOUBLE(2),
        /**
         * 布尔
         */
        BOOLEAN(3),
        /**
         * json对象
         */
        JSON(4);

        private Integer value;

        DataType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value.equals(this.value);
        }
    }

    /**
     * 客户联系方式类型
     */
    public enum CustomerContactType {
        /**
         * 1：手机
         */
        PHONE(1),

        /**
         * 2：座机
         */
        LAND_LINE(2),
        /**
         * 3：微信
         */
        WE_CHAT(3),
        /**
         * 4：QQ
         */
        QQ(4);


        private Integer value;


        CustomerContactType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value.equals(this.value);
        }
    }


    /**
     * websocket消息类型
     * 不同实体对应不同消息类型,类型编号 从 2000 开始，1000预留为ws的固定类型
     */
    public enum WsData {
        /**
         * 通知 Notice
         */
        NOTICE(2000),
        /**
         * 消息 message
         */
        MESSAGE(2001),
        /**
         * 待办todo
         */
        TODO(2002);

        private Integer value;

        WsData(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value != null && value.equals(this.value);
        }
    }

    public enum CheckToken {
        /**
         * 验证成功
         */
        SUCCESS(0),
        /**
         * 其他地方登录
         */
        TOKEN_OUT(1),
        /**
         * 登录过期
         */
        TOKEN_OVERDUE(2),
        /**
         * 未登录
         */
        TOKEN_FAULT(3);
        private int value;

        CheckToken(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 性别枚举
     */
    public enum Gender {

        WOMEN(2, "女"),

        UNKNOWN(0, "未知"),

        MAN(1, "男");

        private Integer value;
        private String code;

        Gender(Integer value, String code) {
            this.value = value;
            this.code = code;
        }

        public Integer getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
