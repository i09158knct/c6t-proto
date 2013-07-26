package jp.knct.di.c6t.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static Date parse(String dateString) throws ParseException {
		return new SimpleDateFormat().parse(dateString);
	}

	public static String format(Date date) {
		return new SimpleDateFormat().format(date);
	}
}
