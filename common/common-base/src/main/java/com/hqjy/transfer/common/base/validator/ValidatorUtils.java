package com.hqjy.transfer.common.base.validator;

import com.hqjy.transfer.common.base.exception.RRException;
import com.hqjy.transfer.common.base.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-15 10:50
 */
@Slf4j
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws RRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new RRException(msg.toString());
        }
    }

    /**
     * 判断number参数是否是浮点数表示方式
     *
     * @param number 数值字符串
     * @return 返回结果
     */
    public static boolean isFloat(String number) {
        number = number.trim();
        //浮点数的正则表达式-小数点在中间与前面
        String pointPrefix = "(\\-|\\+){0,1}\\d*\\.\\d+";
        //浮点数的正则表达式-小数点在后面
        String pointSuffix = "(\\-|\\+){0,1}\\d+\\.";
        return number.matches(pointPrefix) || number.matches(pointSuffix);
    }

    /**
     * 判断number参数是否是整型数表示方式
     *
     * @param number 数值字符串
     * @return 返回结果
     */
    public static boolean isInteger(String number) {
        number = number.trim();
        //整数的正则表达式
        String intNumRegex = "\\-{0,1}\\d+";
        return number.matches(intNumRegex);
    }

    public static boolean isPhone(String phone) {
        if (StringUtils.isNotEmpty(phone)) {
            String regex = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|(19[8|9]))\\d{8}$";
            if (phone.length() != 11) {
                log.error("手机号应为11位数:校验的手机号码" + phone);
                return false;
            } else {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(phone);
                boolean isMatch = m.matches();
                if (!isMatch) {
                    log.error("请填入正确的手机号:校验的手机号码" + phone);
                }
                return isMatch;
            }
        }
        log.info("EXCEL导入手机号码允许为空，可以正常写入该商机数据");
        return true;
    }
}
