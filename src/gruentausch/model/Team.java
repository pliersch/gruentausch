package gruentausch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "liersch.gruentausch")
public class Team extends BaseModel {

	private List<Employee> employees = new ArrayList<Employee>();

	@XmlElement(name = "employee")
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee) {
		List<Employee> old = new ArrayList<>(employees);
		this.employees.add(employee);
		propertyChangeSupport.firePropertyChange("employees", old, this);
	}

}
