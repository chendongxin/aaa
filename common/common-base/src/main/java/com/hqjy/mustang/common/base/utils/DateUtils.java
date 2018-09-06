package com.hqjy.mustang.common.base.utils;

import com.hqjy.mustang.common.base.exception.RRException;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatPattern(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date parse(String date) {
        return parse(date, DATE_TIME_PATTERN);
    }

    /**
     * 将日期字符串转换为日期类型
     *
     * @param date    日期字符串
     * @param pattern 转换模式
     * @return 返回日期类型时间
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RRException("时间转换异常！");
        }
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd）
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    private static String formatDate(Date date, Object... pattern) {
        if (date == null) {
            return null;
        }
        String formatDate;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }


    /**
     * 获取某天的开始时间
     *
     * @param dateTime 字符串日期
     * @return 某天的开始时间
     */
    public static String getBeginTime(String dateTime) {
        if (StringUtils.isNotEmpty(dateTime)) {
            Date date = parse(dateTime, DATE_PATTERN);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf("00"));
            calendar.set(Calendar.MINUTE, Integer.valueOf("00"));
            calendar.set(Calendar.SECOND, Integer.valueOf("00"));
            return formatPattern(calendar.getTime());
        }
        return null;
    }

    /**
     * 获取某天的结束时间
     *
     * @param dateTime 字符串日期
     * @return 某天的结束时间
     */
    public static String getEndTime(String dateTime) {
        if (StringUtils.isNotEmpty(dateTime)) {
            Date date = parse(dateTime, DATE_PATTERN);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return formatPattern(calendar.getTime());
        }
        return null;
    }


    /**
     * 日期往后增加天数
     *
     * @param date    日期
     * @param addDays 天数
     * @return 增多天数后的日期
     */
    private static Date addDay(Date date, int addDays) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDays);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param date1 开始日期 "yyyy-MM-dd"
     * @param date2 结束日期 "yyyy-MM-dd"
     * @return 获取两个日期之间的天数
     */
    public static int countDays(String date1, String date2) {
        Date d1 = parse(date1, DATE_PATTERN);
        Date d2 = parse(date2, DATE_PATTERN);
        int days = (int) ((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24));
        return days + 1;
    }


    /**
     * 将秒数转换为日时分秒，
     */
    public static String secondToTime(long second) {
        //转换小时
        long hours = second / 3600;
        //剩余秒数
        second = second % 3600;
        //转换分钟
        long minutes = second / 60;
        //剩余秒数
        second = second % 60;
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second);
    }

    /**
     * 将时间转换为秒
     *
     * @param time "48:13:35"
     * @return 返回秒数
     */
    public static int timeToSecond(String time) {
        if (StringUtils.isNotEmpty(time)) {
            String[] split = time.split(":");
            Integer hour = Integer.valueOf(split[0]);
            Integer minute = Integer.valueOf(split[1]);
            Integer second = Integer.valueOf(split[2]);
            return hour * 3600 + minute * 60 + second;
        }
        return 0;
    }

    public static void main(String[] args) {
        int addDays = 3;
        System.out.println(addDay(new Date(), addDays));
        System.out.println(formatDate(new Date(), DATE_TIME_PATTERN));
        System.out.println(secondToTime(0));
        System.out.println(timeToSecond(""));
        System.out.println(countDays("2018-07-01", "2018-08-06"));
        String s = DateUtils.secondToTime(Long.valueOf(new DecimalFormat("0").format(3.99)));
        System.out.println(s);


    }
}
