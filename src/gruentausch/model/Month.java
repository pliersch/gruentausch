package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Month extends Unmarshaller.Listener {

	private int month;
	private int year;
	private List<Day> days;
	private Year parent;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

	public void addDay(Day day) {
		if (days == null) {
			days = new ArrayList<>();
		}
		days.add(day);
	}

	// public Day getDay() {
	//
	// }

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.parent = (Year) parent;
	}

	public Year getParent() {
		return parent;
	}

	@XmlTransient
	public void setParent(Year year) {
		this.parent = year;
	}

	@Override
	public String toString() {
		return month + "." + year;
	}

}
