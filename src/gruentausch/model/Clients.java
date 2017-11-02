package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "liersch.gruentausch")
public class Clients extends BaseModel {

	private List<Customer> customers = new ArrayList<Customer>();
	private Adress adress;

	@XmlElement(name = "customers")
	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public void addCustomer(Customer customer) {
		customers.add(customer);
		// TODO can´t return customers copy at the moment
		propertyChangeSupport.firePropertyChange("customers", null, this);
		propertyChangeSupport.firePropertyChange("customer", null, customer);
	}

	public boolean contain(Customer customer) {
		for (Customer c : customers) {
			if (c.getId() == customer.getId()) {
				return true;
			}
		}
		return false;
	}

	public void updateCustomer(Customer person) {
		for (Customer e : customers) {
			if (e.getId() == person.getId()) {
				e = person;
				propertyChangeSupport.firePropertyChange("customers", null, this);
				propertyChangeSupport.firePropertyChange("person", null, person);
				break;
			}
		}
	}

	public void removePerson(Customer customer) {
		for (Customer e : customers) {
			if (e.getId() == customer.getId()) {
				customers.remove(customer);
				propertyChangeSupport.firePropertyChange("customers", null, this);
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

}

