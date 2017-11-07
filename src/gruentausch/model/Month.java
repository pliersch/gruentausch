package gruentausch.model;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

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

	@Override
	public String toString() {
		return month + "." + year;
	}

}
