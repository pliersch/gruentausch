package gruentausch.model;

import java.util.Calendar;

public class Person extends BaseModel {

	// TODO extract superclass IdModel (extends BaseMoldel) ? see Customer class
	private String id = Calendar.getInstance().getTime().toString();
	private String surname;
	private String givenname;

	public Person() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}