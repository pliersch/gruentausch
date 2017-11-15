package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee extends Person {

	private List<Year> years = new ArrayList<Year>();
	private Adress adress;

	public Employee() {
	}
	
	@XmlElement(name = "year")
	public List<Year> getYears() {
		return years;
	}

	// TODO do we need propertyChangeSupport? only "employee" and "employees" are used
	public void setYears(List<Year> years) {
		propertyChangeSupport.firePropertyChange("years", this.years, this.years = years);
	}

	public void addYear(Year year) {
		List<Year> oldYears = years;
		years.add(year);
		propertyChangeSupport.firePropertyChange("years", oldYears, this.years);
	}

	public Year getYear(int year) {
		for (Year y : years) {
			if (y.getYear() == year) {
				return y;
			}
		}
		return null;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
}
