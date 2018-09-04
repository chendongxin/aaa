package com.hqjy.transfer.common.base.utils;

import com.hqjy.transfer.common.base.exception.RRException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 11:33 2018/5/22
 */
public class RegularUtils {


    /**
     * 检验QQ号码格式是否正确
     *
     * @param str 输入QQ号码
     */
    public static void checkQq(String str) {

        if (StringUtils.isNotBlank(str)) {
            String regEx = "^[1-9][0-9]{4,9}$";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.find()) {
                throw new RRException("Q格式错误！并且最长只能10位！");
            }
        }

    }

    /**
     * 检验电话号码格式是否正确
     *
     * @param str 输入座机号码
     */
    public static void checkPhone(String str) {

        if (StringUtils.isNotBlank(str)) {
            String regEx = "^[1-9]{1}[0-9]{5,8}$";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.find()) {
                throw new RRException("电话号码格式错误！");
            }
        }
    }

    /**
     * 检验密码安全强度
     */
    public static void checkPassWord(String str) {
        if (StringUtils.isNotBlank(str)) {
            String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.find()) {
                throw new RRException("密码8-20位，必须包含数字和字母");
            }
        }
    }

    public static void main(String[] args) {
        checkPassWord("1238111114a");
    }
}
