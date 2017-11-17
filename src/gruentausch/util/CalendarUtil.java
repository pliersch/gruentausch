package gruentausch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

	private static final String TIME_FORMAT = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
	// private static final SimpleDateFormat EN_FORMAT = new
	// SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	public static Calendar getCalendar(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, day);
		calendar.set(Calendar.MINUTE, day);
		return calendar;
	}

	public static Calendar getCalendar(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 0);
		return calendar;
	}

	public static Calendar getWorkingTime(String beginTime, String endTime) {
		Calendar begin = convertToFloat(beginTime);
		Calendar end = convertToFloat(endTime);
		Calendar calendar = Calendar.getInstance();
		int hours = end.get(Calendar.HOUR_OF_DAY) - begin.get(Calendar.HOUR_OF_DAY);
		int minutes = end.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		return calendar;
	}

	/**
	 * 
	 * @param string
	 *          Must be like: 2012-12-24
	 */
	public static Calendar toCalendar(String string) {
		String[] split = string.split("-");
		int year = Integer.parseInt(split[0]);
		int month = Integer.parseInt(split[1]);
		int day = Integer.parseInt(split[2]);
		Calendar.getInstance().clear();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0);
		return calendar;
	}

	/**
	 * 
	 * @return returns String like 12.12.2012
	 */
	public static String toGermanString(Calendar calendar) {
		String formatted = DE_FORMAT.format(calendar.getTime());
		return formatted;
	}

	private static Calendar convertToFloat(String time) {
		Calendar calendar = Calendar.getInstance();
		if (time.matches(TIME_FORMAT)) {
			String[] split = time.split(":");
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
			calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));

			return calendar;
		}
		System.out.println("Fehler, kein valides Zeitformat: " + time);
		return null;
	}

	public static String getMonth(int month) {
		switch (month) {
		case 0:
			return "Januar";
		case 1:
			return "Februar";
		case 2:
			return "März";
		case 3:
			return "April";
		case 4:
			return "Mai";
		case 5:
			return "Juni";
		case 6:
			return "Juli";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "Oktober";
		case 10:
			return "November";
		case 11:
			return "Dezember";
		default:
			return "Fehler";
		}
	}
}
