package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee extends BaseModel {

	private static int ID_COUNTER = 0;

	private final int id = ID_COUNTER++;
	private String surname;
	private String givenname;
	private String city;
	private String street;
	private int plz;
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

	public int getId() {
		return id;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		propertyChangeSupport.firePropertyChange("city", this.city, this.city = city);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		propertyChangeSupport.firePropertyChange("street", this.street, this.street = street);
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		propertyChangeSupport.firePropertyChange("plz", this.plz, this.plz = plz);
	}
}
