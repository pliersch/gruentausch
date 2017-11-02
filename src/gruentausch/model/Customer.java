package gruentausch.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "liersch.gruentausch")
public class Customer extends BaseModel {

	private String name;
	// TODO extract superclass IdModel (extends BaseMoldel) ? see Customer class
	private String id = Calendar.getInstance().getTime().toString();
	private List<Person> persons = new ArrayList<Person>();
	private Adress adress;

	// TODO why "person" not "persons"
	// and it could be better extract contact class
	@XmlElement(name = "person")
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public void addPerson(Person person) {
		persons.add(person);
		// TODO can´t return persons copy at the moment
		propertyChangeSupport.firePropertyChange("persons", null, this);
		propertyChangeSupport.firePropertyChange("person", null, person);
	}

	public boolean contain(Person person) {
		for (Person e : persons) {
			if (e.getId() == person.getId()) {
				return true;
			}
		}
		return false;
	}

	public void updatePerson(Person person) {
		for (Person e : persons) {
			if (e.getId() == person.getId()) {
				e = person;
				propertyChangeSupport.firePropertyChange("persons", null, this);
				propertyChangeSupport.firePropertyChange("person", null, person);
				break;
			}
		}
	}

	public void removePerson(Person person) {
		for (Person e : persons) {
			if (e.getId() == person.getId()) {
				persons.remove(person);
				propertyChangeSupport.firePropertyChange("persons", null, this);
				propertyChangeSupport.firePropertyChange("person", null, null);
				break;
			}
		}
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

