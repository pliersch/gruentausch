package gruentausch.model;

import java.util.Calendar;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import gruentausch.util.CalendarUtil;

@XmlRootElement
public class Day extends Unmarshaller.Listener {

	private int day;
	private String begin;
	private String end;
	private boolean vacation;
	private Month parent;
	private Calendar calendar;

	public Day() {

	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public boolean isVacation() {
		return vacation;
	}

	public void setVacation(boolean vacation) {
		this.vacation = vacation;
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
}
