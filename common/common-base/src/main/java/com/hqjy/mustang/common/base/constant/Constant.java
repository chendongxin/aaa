package com.hqjy.mustang.common.base.constant;


import com.hqjy.mustang.common.base.utils.StringUtils;

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

    /**
     * 开放接口前缀
     */
    public static final String API_OPEN_PATH = "/api/public/";
    public static final String API_OPEN_PATH_ANON = API_OPEN_PATH + "**";

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
        QQ(4),
        /**
         * 5：邮箱
         */
        EMAIL(5);


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

    /**
     * 学历
     */
    public enum Education {
        NONE(0, "无"),
        PRIMARY(1, "小学"),
        MIDDLE(2, "初中"),
        HIGH(3, "高中"),
        COLLEGE(4, "大专"),
        UNDERGRADUATE(5, "本科"),
        MASTER(6, "硕士"),
        DOCTORAL(7, "博士");

        private Integer value;

        private String code;

        Education(Integer value, String code) {
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

        /**
         * 获取学历
         */
        public String handleEducationName(String educationName) {
            if (StringUtils.isNotEmpty(educationName)) {
                for (Constant.Education e : Constant.Education.values()) {
                    for (String s : e.getCode().split(",")) {
                        if (educationName.contains(s)) {
                            return e.getCode();
                        }
                    }
                }
            }
            return Constant.Education.NONE.getCode();
        }

    }

    /**
     * 客户状态
     */
    public enum CustomerStatus {

        POTENTIAL(0, "潜在"),

        FAILED_VALID(1, "有效失败"),

        FAILED_INVALID(2, "无效失败"),

        RESERVATION(3, "预约"),

        DEAL(4, "成交");

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
     * 有效失败包括
     */
    public enum ValidType {

        JOB_SEARCH(0, "找工作不学"),
        LOAN_REJECTION(1, "拒绝贷款"),
        GET_JOB(2, "找到工作"),
        SUM_WIN_PART_TIME(3, "寒/暑假工"),
        NO_INTEREST(4, "没投过简历/无兴趣"),
        PASSIVE_MESSAGE(5, "负面信息"),
        OTHER_JOB(6, "想从事其他岗位"),
        INTERNAL_SCHOOL(7, "在校生不能离校"),
        AGE_OVERSTEP(8, "年龄＜18岁或＞30岁"),
        EXPERIENCE_ONE_YEAR(9, "已有同岗位经验1年以上"),
        OTHER(10, "其他");

        private Integer value;

        private String code;

        ValidType(Integer value, String code) {
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
     * 无效失败包括
     */
    public enum Valid {

        SAME_TRAIN(0, "参加过同类培训"),
        LOW_DEGREE(1, "小学初中学历"),
        ADVERTISING_INFORMATION(2, "广告信息"),
        PEER_SPY(3, "同行探子");

        private Integer value;

        private String code;

        Valid(Integer value, String code) {
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
     * 跟进状态
     */
    public enum FollowStatus {

        POTENTIAL("A潜在", 0),
        VALID_DATA("B(失败)有效", 1),
        INVALID_DATA("C(失败)无效", 2),
        RESERVATION("D预约", 3),
        DEAL("E成交", 4);

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

    /**
     * 推广方式
     */
    public enum GetWayStatus {

        ACTIVE_GET("主动获取", 1),
        PASSIVE_GET("被动获取", 2);
        private int value;
        private String code;

        GetWayStatus(String code, int value) {
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

}
