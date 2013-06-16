package com.philit.ehr.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTools {

	@SuppressLint("SimpleDateFormat")
	public static String timestampToStr(long timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(timestamp);
		return sdf.format(date);
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isToday(long timestamp) {
		Date date = new Date();
		Date today = new Date(date.getYear(), date.getMonth(), date.getDate());
		if (timestamp < today.getTime())
			return false;
		else
			return true;
	}
	
	@SuppressWarnings("deprecation")
	public static long getTodayStartTime() {
	    Date date = new Date();
        Date today = new Date(date.getYear(), date.getMonth(), date.getDate());
	    return today.getTime();
	}
	
	public static long getTodayEndTime() {
	    return getTodayStartTime() + 1000 * 60 * 60 * 24;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isNDaysAgo(long timestamp,int n) {
		Date date = new Date();
		Date today = new Date(date.getYear(), date.getMonth(), date.getDate());
		int day = (int)((today.getTime() -1 - timestamp) / (24 * 60 * 60 * 1000));  
		if(n-1 < day + 1 && day + 1<=n)
			return true;
		else
			return false;
	}
	
	public static String getTimeLength(long time) {
	    long hour = time / (1000 * 60 * 60);
        long min = time / (1000 * 60) - hour * 60;
        long sec = time / 1000 - hour * 60 * 60 - min * 60;
        String hourStr = String.valueOf(hour).length() == 1 ? "0" + String.valueOf(hour) : String.valueOf(hour);
        String minStr = String.valueOf(min).length() == 1 ? "0" + String.valueOf(min) : String.valueOf(min);
        String secStr = String.valueOf(sec).length() == 1 ? "0" + String.valueOf(sec) : String.valueOf(sec);
        return hourStr + ":" + minStr + ":" + secStr;
        //return  minStr + ":" + secStr;
	}
	
	public static String getWeek(Date date) {
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}
	
	public static long getTimeInMillis(int year, int month, int day, int hour,
			int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		if (year <= -1)
			year = calendar.get(Calendar.YEAR);
		if (month <= -1)
			month = calendar.get(Calendar.MONTH);
		if (day <= -1)
			day = calendar.get(Calendar.DAY_OF_MONTH);
		if (hour <= -1)
			hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (minute <= -1)
			minute = calendar.get(Calendar.MINUTE);
		if (second <= -1)
			second = calendar.get(Calendar.SECOND);
		calendar.set(year, month, day, hour, minute, second);
		return calendar.getTimeInMillis();
	}

	public static int getSecond(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.SECOND);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.SECOND);
	}

	public static int getMinute(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.MINUTE);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.MINUTE);
	}

	public static int getHour(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getDay(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.MONTH);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.MONTH);
	}

	public static int getYear(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		if (timestamp <= 0)
			return calendar.get(Calendar.YEAR);
		calendar.setTimeInMillis(timestamp);
		return calendar.get(Calendar.YEAR);
	}
}
