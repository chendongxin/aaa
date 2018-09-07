package com.hqjy.mustang.common.base.constant;


/**
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
     * 不允许查看公海的开始时间
     */
    public static final String NO_COMMON_START_TIME = " 00:00:00";

    /**
     * 不允许查看公海的结束时间
     */
    public static final String NO_COMMON_END_TIME = " 10:00:00";

    /**
     * 领回自己之前的商机时间
     */
    public static final String PERSONAGE_COMMON_START_TIME = " 09:45:00";
    /**
     * 回收规则：每天九点
     */
    public static final String CUSTOMER_RECYCLE_TIME = " 21:00:00";

    /**
     * 服务内部调用前缀
     */
    public static final String API_PATH = "/api/private/";
    public static final String API_PATH_ANON = API_PATH + "**";

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 菜单
         */
        MENU(0),
        /**
         * 按钮
         */
        BUTTON(1);

        private Integer value;

        MenuType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private Integer value;

        CloudService(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 数据源驱动类型
     */
    public enum DataSourceType {
        /**
         * mysql驱动
         */
        MYSQL(0, "com.mysql.jdbc.Driver"),
        /**
         * oracle驱动
         */
        ORACLE(1, "oracle.jdbc.driver.OracleDriver"),
        /**
         * pgsql驱动
         */
        PGSQL(2, "org.postgresql.Driver");

        private Integer key;
        private String value;

        DataSourceType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }


        public String getValue() {
            return value;
        }
    }

    /**
     * 根据类型获取枚举中的驱动
     */
    public static String getDriver(Integer key) {
        if (key == 0) {
            return DataSourceType.MYSQL.getValue();
        }
        if (key == 1) {
            return DataSourceType.ORACLE.getValue();
        }
        if (key == 2) {
            return DataSourceType.PGSQL.getValue();
        }
        return null;
    }

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
         * 0：手机
         */
        PHONE(0, "手机"),
        /**
         * 1：微信
         */
        WEI_XIN(1, "微信"),
        /**
         * 2：QQ
         */
        QQ(2, "QQ"),
        /**
         * 3：邮箱
         */
        EMAIL(3, "邮箱"),
        /**
         * 4：座机
         */
        LAND_LINE(4, "座机");

        private Integer value;
        private String label;

        CustomerContactType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public boolean equals(Integer value) {
            return value.equals(this.value);
        }
    }

    /**
     * 客户状态
     */
    public enum CustomerStatus {

        POTENTIAL(0, "潜在"),

        INVALID(1, "无效"),

        RESERVATION(2, "预约"),

        DEAL(3, "成交");

        private Integer value;

        private String code;

        CustomerStatus(Integer value, String code) {
            this.value = value;
            this.code = code;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value.equals(this.value);
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 质检状态
     */
    public enum InspectStatus {
        /**
         * 未质检
         */
        NO_INSPECT(0),
        /**
         * 已质检
         */
        HAS_INSPECT(1);

        private Integer value;

        InspectStatus(Integer value) {
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
     * 到访状态
     */
    public enum VisitStatus {

        LOST(-1, "到访流失"),

        NOT(0, "未到访"),

        YES(1, "已到访");

        private Integer value;
        private String code;

        VisitStatus(Integer value, String code) {
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
     * 角色
     */
    public enum Role {

        /**
         * 普通坐席
         */
        SALE("SALE"),

        /**
         * 普通客服
         */
        SERVICE("SERVICE"),
        /**
         * 普通网销销售
         */
        NET_SALES_ORDINARY("NET_SALES_ORDINARY"),

        /**
         * 质检员
         */
        INSPECTION("INSPECTION");

        private String code;

        Role(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * 商机来源
     */
    public enum BizSource {
        /**
         * 系统录入
         */
        SYSTEM(0),
        /**
         * 官网
         */
        OFFICIAL(1),
        /**
         * 客服
         */
        CUSTOMER_SERVICE(2),
        /**
         * 代理网录入
         */
        PROXY(3),
        /**
         * 文件导入
         */
        FILEIMPORT(4);

        private Integer value;

        BizSource(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public boolean equals(Integer value) {
            return value != null && value.equals(this.value);
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


    /**
     * 销售类型 create by xyq on2018年7月27日15:25:14
     */
    public enum SaleType {

        NET_SALE("电销", 0),
        TEL_SALE("网销", 1);

        private Integer value;
        private String code;

        SaleType(String code, Integer value) {
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
     * 跟进状态
     */
    public enum FollowStatus {

        RESERVATION("A预约", 1),
        STRONG_INTENTIONAL("B强意向", 2),
        INTENTIONAL("C意向", 3),
        NO_INTENTIONAL("D无意向", 4),
        NOT_ANSWER("E未接通", 5),
        INVALID_DATA("F无效数据", 6),
        MEDICAL("H医学类", 7),
        DEAL("S成交", 8);

        private int value;
        private String code;

        FollowStatus(String code, int value) {
            this.value = value;
            this.code = code;
        }

        public int getValue() {
            return value;
        }

        public String getCode() {
            return code;
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

}
