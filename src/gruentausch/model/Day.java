package gruentausch.model;

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import gruentausch.util.CalendarUtil;

@XmlRootElement
public class Day extends Unmarshaller.Listener {

	private int day;
	private boolean vacation;
	private Month parent;
	private Calendar calendar;
	private List<Activity> activities;

	public Day() {

	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isVacation() {
		return vacation;
	}

	public void setVacation(boolean vacation) {
		this.vacation = vacation;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.parent = (Month) parent;
	}

	public Month getParent() {
		return parent;
	}
	
	public Calendar toCalendar() {
		int month = parent.getMonth();
		int year = parent.getParent().getYear();
		return CalendarUtil.getCalendar(year, month, day);
	}

	public Calendar getCalendar() {
		if (calendar == null) {
			calendar = toCalendar();
		}
		return calendar;
	}

	@XmlTransient
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getBegin() {
		String begin = null;
		if (activities != null && activities.get(0) != null) {
			begin = activities.get(0).getBegin();
		}
		return begin;
	}

	public String getEnd() {
		String end = null;
		if (activities != null) {
			Activity activity = activities.get(activities.size() - 1);
			end = activity.getEnd();
		}
		return end;
	}

}
