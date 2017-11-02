package gruentausch.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Adress extends BaseModel {

	private String city;
	private String street;
	private int plz;

	public Adress() {
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
