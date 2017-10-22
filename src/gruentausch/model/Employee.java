package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee extends BaseModel {
	private String surname;
	private String givenname;
	private List<Year> years = new ArrayList<Year>();

	public Employee() {

	}

	public Employee(String givenname, String surname) {
		super();
		this.givenname = givenname;
		this.surname = surname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String name) {
		propertyChangeSupport.firePropertyChange("surname", this.surname, this.surname = name);
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String name) {
		propertyChangeSupport.firePropertyChange("givenname", this.givenname, this.givenname = name);
	}

	@XmlElement(name = "year")
	public List<Year> getYears() {
		return years;
	}

	public void setYears(List<Year> years) {
		propertyChangeSupport.firePropertyChange("years", this.years, this.years = years);
	}

	public void addYear(Year year) {
		List<Year> oldYears = years;
		years.add(year);
		propertyChangeSupport.firePropertyChange("years", oldYears, this.years);

	}
}
