package jp.knct.di.c6t.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static Date parse(String dateString) throws ParseException {
		return new SimpleDateFormat().parse(dateString);
	}

	public static String format(Date date) {
		return new SimpleDateFormat().format(date);
	}

	public static String formatOnlyDate(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd").format(date);
	}

	public static Date getNextDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date getPreviousDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static boolean isSameDay(Date date1, Date date2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date1).equals(fmt.format(date2));
	}
}
