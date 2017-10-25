package gruentausch.parts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import gruentausch.model.Day;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;
import gruentausch.views.TimeTableView;

public class TimeTablePart extends TimeTableView {

	@Inject
	void updateMonth(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Month month) {
		if (month != null) {
			if (viewer != null /* && !viewer.isDisposed() */) {
				List<Day> days = addEmptyDays(month);
				viewer.setInput(days);
				Table table = viewer.getTable();
				TableItem[] items = table.getItems();
				Day day;
				Calendar calendar;
				Color weekendColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
				Color missingColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);

				for (TableItem tableItem : items) {
					day = (Day) tableItem.getData();
					calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), day.getDay());
					if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
							|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						tableItem.setBackground(weekendColor);
					}
					else if (!day.isVacation() && day.getBegin() == null) {
						tableItem.setBackground(missingColor);
					}
				}
			}
		}
	}

	private List<Day> addEmptyDays(Month month) {
		Calendar calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Day> employeeDays = month.getDays();
		List<Day> days = new ArrayList<>(daysInMonth);

		int position = 0;

		for (int i = 0; i < daysInMonth; i++) {
			Day day = employeeDays.get(position);
			if (day.getDay() == i + 1) {
				position++;
				days.add(day);
			} else {
				Day newDay = new Day();
				newDay.setDay(i + 1);
				days.add(newDay);
			}

		}
		return days;
	}

}
