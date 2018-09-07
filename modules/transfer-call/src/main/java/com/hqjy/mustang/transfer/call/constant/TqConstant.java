package com.hqjy.mustang.transfer.call.constant;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 17:10
 */
public class TqConstant {

    public static String state(int code) {
        switch (code) {
            case 1:
                return "成功";
            case 0:
                return "密码不对";
            case -1:
                return "token不对";
            case -2:
                return "uin不合法";
            case -3:
                return "token长度不对";
            case -4:
                return "服务器内部错误";
            case -5:
                return "用户不存在";
            case -8:
                return "用户名和TQ号同时为空了";
            case -14:
                return "认证类型不对";
            case -15:
                return "时间错误";
            case -16:
                return "来源IP错误";
            case -17:
                return "获取管理员号码错误";
            case -18:
                return "rsa字符串解码失败";
            case -19:
                return "ip被禁用";
            case -20:
                return "工作日被禁用";
            case -21:
                return "时段被禁用";
            default:
                return "未知的错误";
        }
    }

}
