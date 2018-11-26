package com.xiongms.libcore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeekUtil {
    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        //c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        //两年交接处，如果周数是1（第二年的第一周），但在本月中不是第一周（年末）
        if ((c.get(Calendar.WEEK_OF_YEAR) == 1) && (c.get(Calendar.WEEK_OF_MONTH) != 1)) {
            c.add(Calendar.DATE, -7);
            return c.get(Calendar.WEEK_OF_YEAR) + 1;
        } else {
            return c.get(Calendar.WEEK_OF_YEAR);
        }
    }

    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        //当年1月1日的日期
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     * 即，星期天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     * 即，星期六
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 取得当前年份所有周的时间段
     * 返回"2013-01-06~2013-01-12,2013-01-06~2013-01-12"
     *
     * @return
     */
    public static String getAllWeekJson(String format, int year) {
        String weekstr = "";
        int i = 0;
        for (i = 1; i <= getMaxWeekNumOfYear(year); i++) {
            Date fd = getFirstDayOfWeek(year, i);
            Date ed = getLastDayOfWeek(year, i);

            Calendar fc = new GregorianCalendar();
            Calendar ec = new GregorianCalendar();
            fc.setTime(fd);
            ec.setTime(ed);

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            weekstr += "\"week" + i + "\":\"" + sdf.format(fc.getTime()) + "~" + sdf.format(ec.getTime()) + "\",";
        }
        return "{" + weekstr.substring(0, weekstr.length() - 1) + "}";
    }
}