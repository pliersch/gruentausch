package gruentausch.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.model.Year;

public class WorkingTimeUtil {

	public List<Day> getUnresolvedWorkingDays(Employee employee) {
		Year year = employee.getYears().get(0);
		Month month = year.getMonths().get(0);
		List<Day> days = month.getDays();
		List<Day> unresolvedDays = new ArrayList<>();
		Calendar calendar = CalendarUtil.getCalendar(year.getYear(), month.getMonth(), 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int size = days.size();
		int position = 0;
		for (int i = 0; i < daysInMonth; i++) {
			if (i == size) {
				unresolvedDays.add(new Day());
				System.out.println("unresolved: " + i);
			} else {
				Day day = days.get(position);
				if (day.getDay() == i + 1) {
					position++;
					if (!isDayComplete(day)) {
						if (!isWeekend(year, month, i + 1)) {
							System.out.println("unresolved: " + i);
							unresolvedDays.add(new Day());
						}
						
					}
				} else {
					if (!isWeekend(year, month, i + 1)) {
						System.out.println("unresolved: " + i);
						unresolvedDays.add(new Day());
					}
					size++;
				}
			}
		}
		return unresolvedDays;
	}

	private boolean isWeekend(Year year, Month month, int i) {
		Calendar calendar = CalendarUtil.getCalendar(year.getYear(), month.getMonth(), i);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	private boolean isDayComplete(Day day) {
		if (day.getBegin() != null && day.getEnd() != null || day.isVacation()) {
			return true;
		}
		return false;
	}

}
