package com.zyxm.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author Robert
 * @Create 2020/9/16
 * @Desc TODO 日期工具类
 **/
public class DateUtils {
    private static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_HMS = "HH:mm:ss";
    private static final String DATE_FORMAT_YM = "yyyy-MM";
    private static final String DATE_FORMAT_TIMESTAMP = "yyyyMMddHHmmss";

    public static String getTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHMS));
    }

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT_YMD));
    }

    public static String getDateYM() {
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT_YM));
    }

    public static String dateFormat(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateFormat(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    public static String dateFormatYMDHMS(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return dateFormat.format(date);
    }

    public static String dateFormatTimestamp(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT_TIMESTAMP));
    }

    public static String getTimestamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT_TIMESTAMP));
    }

    /**
     * 随机文件夹
     */
    public static String dataToString() {
        return LocalDate.now().toString().replace("-", "/");
    }
}
