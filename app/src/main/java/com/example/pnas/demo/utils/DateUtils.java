package com.example.pnas.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*****************
 * @author Justin_xiaozongkang
 * @date 2015/12/31
 * @describ 日期处理类
 ******************/
public class DateUtils {
    /**
     * 将日期截取 月和日
     * @param strDate 日期字符串"2015-12-31"
     * @return "12-31"
     */
    public static String changeWeekDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        String str = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
            str = (date.getMonth() + 1) + "-" + date.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取当前时间所在年的第几周
     * @param date 日期字符串"2015-12-31"
     * @return "52"
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间所在年的第几周
     * @param strdate 日期字符串"2015-12-31"
     * @return "52"
     */
    public static int getWeekOfYear(String strdate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 计算某年某周的开始日期
     *
     * @param yearNum
     *            格式 yyyy ，必须大于1900年度 小于9999年
     * @param weekNum
     *            1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getYearWeekFirstDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        // 分别取得当前日期的年、月、日
        return getFormatDate(cal.getTime());
    }

    /**
     * 计算某年某周的结束日期
     *
     * @param yearNum
     *            格式 yyyy ，必须大于1900年度 小于9999年
     * @param weekNum
     *            1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getYearWeekEndDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        return getFormatDate(cal.getTime());
    }

    /**
     * 格式化日期
     * @param date
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getFormatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
