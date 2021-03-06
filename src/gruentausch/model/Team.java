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
		employees.add(employee);
		// TODO can�t return team copy at the moment
		propertyChangeSupport.firePropertyChange("employees", null, this);
		propertyChangeSupport.firePropertyChange("employee", null, employee);
	}

	public boolean contain(Employee employee) {
		for (Employee e : employees) {
			if (e.getId() == employee.getId()) {
				return true;
			}
		}
		return false;
	}

	public void updateEmployee(Employee employee) {
		for (Employee e : employees) {
			if (e.getId() == employee.getId()) {
				e = employee;
				propertyChangeSupport.firePropertyChange("employees", null, this);
				propertyChangeSupport.firePropertyChange("employee", null, employee);
				break;
			}
		}
	}

	public void removeEmployee(Employee employee) {
		for (Employee e : employees) {
			if (e.getId().equals(employee.getId())) {
				employees.remove(e);
				propertyChangeSupport.firePropertyChange("employees", null, this);
				propertyChangeSupport.firePropertyChange("employee", null, null);
				break;
			}
		}
	}

}

