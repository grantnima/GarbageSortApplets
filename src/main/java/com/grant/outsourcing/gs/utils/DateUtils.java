package com.grant.outsourcing.gs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

	/**
	 * 获得下月1号零时零分零秒
	 * @return 当月1号时间
	 */
	public static Date nextMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间下个整点时间
	 * @return 时间对象
	 */
	public static Date getNextWholePoint(){
		return new Date(System.currentTimeMillis()/(1000*60*60)*(1000*60*60)+3600000);
	}

	/**
	 * 获取今天零点时间
	 * @return 时间对象
	 */
	public static Date getTodayZeroTime(){
		long zero = System.currentTimeMillis()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();
		return new Date(zero);
	}

	/**
	 * 获取某天的零点时间
	 * @return 时间对象
	 */
	public static Date getZeroTime(Date time){
		TimeZone curTimeZone = TimeZone.getTimeZone("GMT+8");
		Calendar c = Calendar.getInstance(curTimeZone);
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取某天的零点时间
	 * @return 时间对象
	 */
	public static Date getZeroTime(Long time){
		TimeZone curTimeZone = TimeZone.getTimeZone("GMT+8");
		Calendar c = Calendar.getInstance(curTimeZone);
		c.setTime(new Date(time));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取未来时间
	 * @param day 天数
	 * @return 时间对象
	 */
	public static Date getFutureDate(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Math.abs(day));
		return calendar.getTime();
	}

	/**
	 * 获取未来时间
	 * @param date 需要操作的时间
	 * @param millisecond 毫秒数
	 * @return 时间对象
	 */
	public static Date getFutureDate(Date date,Long millisecond){
		return new Date(date.getTime()+millisecond);
	}

	/**
	 * 获取以前时间
	 * @param date 需要操作的时间
	 * @param millisecond 毫秒数
	 * @return 时间对象
	 */
	public static Date getBeforeDate(Date date,Long millisecond){
		return new Date(date.getTime()-millisecond);
	}

	/**
	 * 获取以前时间
	 * @param date 需要操作的时间
	 * @param millisecond 毫秒数
	 * @return 时间对象
	 */
	public static Date getBeforeDate(Date date,Integer millisecond){
		return new Date(date.getTime()-millisecond);
	}

	/**
	 * 获取以前时间
	 * @param day 天数
	 * @return 时间对象
	 */
	public static Date getBeforeDate(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -Math.abs(day));
		return calendar.getTime();
	}

	/**
	 * 格式化时间
	 * @param pattern 时间格式
	 * @return 时间格式后字符串
	 */
	public static String formatDate(Date time,String pattern){
		if (time != null){
			SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
			return dateFormater.format(time);
		}
		return null;
	}

	/**
	 * 格式化时间
	 * @return 时间格式后字符串
	 */
	public static String formatDate(Date time){
		if (time != null){
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormater.format(time);
		}
		return null;
	}

	/**
	 * 把yyyy-MM-dd HH:mm:ss 转Date
	 * @param dateString
	 * @return
	 */
	public static Date convertStringToDate(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (dateString != null && dateString.trim().length() > 0){
				if (dateString.length() == 16) {
					dateString = dateString+":00";
				}
			}
			return dateFormat.parse(dateString);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 把yyyy-MM-dd 转Date
	 * @param dateString
	 * @return
	 */
	public static Date convertStringToShortDate(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (dateString != null && dateString.trim().length() > 0){
				if (dateString.length() == 16) {
					dateString = dateString+":00";
				}
			}
			return dateFormat.parse(dateString);
		} catch (Exception ex) {
			return null;
		}
	}

	private static String changeTime(String time){
		if (time != null && time.trim().length() > 0){
			if (time.length() == 16) {
				return time+":00";
			} else {
				return time;
			}
		}
		return null;
	}

	/**
	 * 获取当前时间的整点时间
	 * @return 时间对象
	 */
	public static Date getWholePoint(){
		return new Date(System.currentTimeMillis()/(1000*60*60)*(1000*60*60));
	}

	/**
	 * 获得当月1号零时零分零秒
	 * @return 当月1号时间
	 */
	public static Date initDateByMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Map<String,Date> get(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		//将分钟至0
		calendar.set(Calendar.MINUTE, 0);
		//将秒至0
		calendar.set(Calendar.SECOND,0);
		//将毫秒至0
		calendar.set(Calendar.MILLISECOND, 0);
		//获得当前月第一天
		Date sdate = calendar.getTime();
		//将当前月加1；
		calendar.add(Calendar.MONTH, 1);
		//在当前月的下一月基础上减去1毫秒
		calendar.add(Calendar.MILLISECOND, -1);
		//获得当前月最后一天
		Date edate = calendar.getTime();
		Map<String,Date> map = new HashMap<>();
		map.put("start_time",sdate);
		map.put("end_time",edate);
		return map;
	}

	/**
	 * 获取某月第一天
	 * @param month
	 * @return
	 */
	public static Date getMinDateMonth(String month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Date nowDate=sdf.parse(month);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			return calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取某月最后一天
	 * @param month
	 * @return
	 */
	public static Date getMaxDateMonth(String month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Date nowDate=sdf.parse(month);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getCertainWeekDays (String dayStr) {
		List<String> response = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] split = dayStr.split("-");
		int year = Integer.valueOf(split[0]);
		int month = Integer.valueOf(split[1]);
		int day = Integer.valueOf(split[2]);
		calendar.set(year, month - 1, day);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		for (int i = 0; i < 7; i++) {
			response.add(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DATE, 1);
		}
		return response;
	}

	public static List<String> getWeekDays() {
		List<String> response = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		for (int i = 0; i < 7; i++) {
			response.add(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DATE, 1);
		}
		return response;
	}

}
