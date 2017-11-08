package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Year {

	private int year;
	private List<Month> months = new ArrayList<Month>();
	private Employee parent;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Month> getMonths() {
		return months;
	}

	public void setMonths(List<Month> months) {
		this.months = months;
	}

	public void addMonth(Month month) {
		months.add(month);
		
	}

	public Employee getParent() {
		return parent;
	}

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.parent = (Employee) parent;
	}

}
