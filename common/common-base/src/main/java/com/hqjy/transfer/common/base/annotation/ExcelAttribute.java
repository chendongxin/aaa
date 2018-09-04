package com.hqjy.transfer.common.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xieyuqing
 * @ description Excel配置注解自定义接口
 * @ date create in 2018/6/5 11:35
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAttribute {

    /**
     * Excel中的列名
     *
     * @return 返回值
     */
    String name();

    /**
     * 列名对应的A,B,C,D...,不指定按照默认顺序排序
     *
     * @return 返回值
     */
    String column() default "";

    /**
     * 提示信息
     *
     * @return 返回值
     */
    String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容
     *
     * @return 返回值
     */
    String[] combo() default {};

    /**
     * 是否导出数据
     *
     * @return 返回值
     */
    boolean isExport() default true;

    /**
     * 是否为重要字段（整列标红,着重显示）
     *
     * @return 返回值
     */
    boolean isMark() default false;

    /**
     * 是否合计当前列
     *
     * @return 返回值
     */
    boolean isSum() default false;
}
