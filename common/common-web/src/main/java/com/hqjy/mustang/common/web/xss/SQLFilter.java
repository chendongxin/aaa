package com.hqjy.mustang.common.web.xss;


import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.StringUtils;

/**
 * SQL过滤
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 16:16
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = str.replaceAll("'", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll(";", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                throw new RRException("包含非法字符");
            }
        }

        return str;
    }
}
