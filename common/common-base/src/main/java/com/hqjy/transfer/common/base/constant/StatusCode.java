package com.hqjy.transfer.common.base.constant;

/**
 * 业务状态码
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:25
 */
public enum StatusCode {
    FAILURE(-1, "失败"),
    NO_EXIT(1, "查询数据不存在"),
    SUCCESS(0, "成功"),
    PARAMETER_ERROR(1001, "提交参数不符合规范"),
    USERTOKEN_PARAMETER_ERROR(1002, "Token参数错误"),
    LOGIN_FAILURE(1110, "登录失败"),
    LOGIN_EXCESSIVE_ATTEMPTS(1111, "登录失败次数太多，请30分钟后再试"),
    LOGIN_VERIFYCODE_ERROR(1112, "验证码错误"),
    LOGIN_PASSWORD_ERROR(1113, "用户密码错误"),
    LOGIN_USER_NOEXIST(1114, "用户不存在"),
    LOGIN_USER_LOCK(1115, "此用户已被禁用"),
    LOGIN_ACCOUNT_NOEXIST(1116, "企业账户不存在"),
    LOGIN_ACCOUNT_LOCK(1117, "企业账户被禁用"),
    USER_UNAUTHORIZED(1118, "用户无权限进行此操作"),
    TOKEN_OVERDUE(1130, "登录过期，请重新登录"),
    TOKEN_OUT(1131, "登录失效，帐号在其他地方登录"),
    TOKEN_FAULT(1132, "未登录，无权限访问"),
    TINET_NO_LOGIN(1200, "用户未登录天润"),
    CNO_NO_BIND(1201, "坐席号未绑定野马用户"),
    DATABASE_DUPLICATEKEY(2001, "数据库中已存在该记录"),
    DATABASE_SAVE_FAILURE(2002, "添加失败"),
    DATABASE_UPDATE_FAILURE(2003, "修改失败"),
    DATABASE_DELETE_FAILURE(2004, "删除失败"),
    DATABASE_SELECT_FAILURE(2005, "资源不存在"),
    DATABASE_NOT_CHANGE(2006, "未作任何修改"),
    DATABASE_UPDATE_ROOT(2101, "根节点，不允许修改"),
    DATABASE_DELETE_ROOT(2102, "根节点，不允许删除"),
    DATABASE_UPDATE_CHILD(2103, "存在子节点，不允许修改"),
    DATABASE_DELETE_CHILD(2104, "存在子节点，不允许删除"),
    PERMISSION_UNAUTHORIZED(2201, "不允许越权操作"),
    PERMISSION_ONESELF(2202, "不允许对自己进行操作"),
    API_CALL_FAILED(3001, "接口调用失败"),
    NC_REQUEST_SAVE_SUCCESS(3100, "请求nc保存接口成功返回NCId!"),
    NC_REQUEST_SAVE_FAULT(3101, "请求nc保存接口处理失败,手机号码已存在!"),
    NC_REQUEST_SAVE_EXCEPTION(3102, "请求nc保存接口异常!"),
    NC_REQUEST_UPDATE_SUCCESS(3103, "请求nc更新接口NC端更新成功!"),
    NC_REQUEST_UPDATE_FAULT(3104, "请求nc更新接口NC端更新失败!"),
    NC_REQUEST_UPDATE_EXCEPTION(3105, "请求NC更新接口异常!"),
    BIZ_CUSTOMER_SAVE_EXCEPTION(4000, "新增客户异常!"),
    BIZ_CUSTOMER_UPDATE_FAULT(4001, "更新客户失败!"),
    BIZ_CUSTOMER_TRANSFER_EXCEPTION(4002, "客户转移异常!"),
    BIZ_CUSTOMER_RETURN_COMMON_EXCEPTION(4003, "客户退回公海异常!"),
    BIZ_CUSTOMER_RECEIVE_EXCEPTION(4004, "公海领取客户异常!"),
    BIZ_CUSTOMER_RESERVE_EXCEPTION(4005, "客户预约异常!"),
    BIZ_CUSTOMER_TRANSFER_TO_NC_EXCEPTION(4006, "客户转移NC异常!"),
    BIZ_CUSTOMER_INVALID_EXCEPTION(4007, "客户无效操作异常!"),
    BIZ_CUSTOMER_INSPECT_EXCEPTION(4008, "客户质检操作异常!"),
    BIZ_FOLLOW_SAVE_FAULT(4009, "保存跟进记录：新增失败!"),
    BIZ_FOLLOW_NOT_ALLOW_SAVE(4010, "当前该客户流程处于公海中，不允许跟进!"),
    BIZ_FOLLOW_UPDATE_PROCESS_FAULT(4011, "保存跟进记录:跟新流程信息失败!"),
    BIZ_PROCESS_ACTIVE_NOT_EXIT(4012, "客户不存在激活状态的流程"),
    BIZ_PROCESS_UPDATE_INACTIVE(4013, "设置客户流程失效失败"),
    BIZ_PROCESS_SAVE_FAULT(4014, "新增客户流程失败"),
    BIZ_PROCESS_INACTIVE_OR_NOT_PRIVATE(4015, "当前流程已不属私海或为过期状态"),
    BIZ_PROCESS_INACTIVE_OR_NOT_COMMON(4016, "当前流程已不属公海或为过期状态"),
    BIZ_CUSTOMER_NOT_POTENTIAL(4017, "客户非潜在状态，不允许退回!"),
    BIZ_CUSTOMER_RECEIVE_REACH_MAX_LIMIT(4018, "客户领取已达上限!"),
    BIZ_CUSTOMER_TRANSFER_TO_NC_NOT_NC_ID(4019, "无法从NC平台获取NCId，该商机不能转移到NC"),
    BIZ_CUSTOMER_CONTACT_IS_NULL(4020, "客户无主联系手机号码，不能转移NC"),
    IMPORT_EXCEL_EXCEPTION(5000, "导入文件格式不正确，请下载指定模板编写再导入"),
    EXPORT_EXCEL_EXCEPTION(5001, "导出报表数据处理异常"),
    BIZ_CUSTOMER_HAS_EXIT(5002, "商机已经存在");


    private final int code;
    private final String msg;

    private StatusCode(int code, String msg) {
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