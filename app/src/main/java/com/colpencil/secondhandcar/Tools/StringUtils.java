package com.colpencil.secondhandcar.Tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/24.
 */
public class StringUtils {

    /**
     * 保留两位小数
     * @param value
     * @return
     */
    public static String doubleFormat(double value){
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(value);
    }

    /**
     * 提取出城市或者县
     * @param city
     * @param district
     * @return
     */
    public static String extractLocation(final String city, final String district){
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }

    /**
     * 年月日 时 分
     * @param time
     * @return
     */
    public static String format(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 年月日 时 分
     * @param time
     * @return
     */
    public static String formatTime(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 年月日
     * @param time
     * @return
     */
    public static String formatDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 年月
     * @param time
     * @return
     */
    public static String formatYear(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(time);
        return sdf.format(date);
    }
}
