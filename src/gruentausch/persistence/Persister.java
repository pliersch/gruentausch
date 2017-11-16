package gruentausch.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import gruentausch.model.Clients;
import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.model.Team;
import gruentausch.util.CalendarUtil;
import gruentausch.util.FileAndFolderManager;
import gruentausch.util.Logger;
import gruentausch.util.XMLManager;

public class Persister {

	private static Persister instance;
	private Team team;
	private Clients clients;

	private Persister() {

	}

	public static Persister getInstance() {
		if (Persister.instance == null) {
			Persister.instance = new Persister();
		}
		return Persister.instance;
	}


	public boolean update(Employee employee) {
		File file = new XMLManager().writeFile(team, "data/Mitarbeiter.xml");
		return false;
	}

	public Employee getEmployee(String emloyeeRootFolder) {
		String pathToXml = emloyeeRootFolder + "\\data.xml";
		Employee employee = (Employee) new XMLManager().readFile(pathToXml, Employee.class);
		return employee;
	}

	public boolean addEmployee(Employee employee) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		try {
			createEmployee(employee);
			createYear(employee, year);
			createMonth(employee, year, month);
			createDay(employee, year, month, day);
		} catch (IOException e) {
			Logger.log(e.getMessage());
			return false;
		}
		return true;
	}

	private String getPath(Employee employee) {
		String name = employee.getGivenname() + " " + employee.getSurname();
		String path = "data/Mitarbeiter/" + name;
		return path;
	}

	private void createEmployee(Employee employee) throws IOException {
		String path = getPath(employee);
		FileAndFolderManager.createFolder(path);
		new XMLManager().writeFile(employee, path + "/data.xml");
	}

	public void createYear(Employee employee, int year) throws IOException {
		String path = getPath(employee) + "/" + year;
		FileAndFolderManager.createFolder(path);
	}

	public void createMonth(Employee employee, int year, int month) throws IOException {
		String path = getPath(employee) + "/" + year + "/";
		String folderName = month + " " + CalendarUtil.getMonth(month - 1);
		FileAndFolderManager.createFolder(path + folderName);

	}

	public boolean createDay(Employee employee, int year, int month, int day) throws IOException {
		String folderName = month + " " + CalendarUtil.getMonth(month - 1);
		String path = getPath(employee) + "/" + year + "/" + folderName + "/" + "Arbeitszeiten.xml";

		// Year y = employee.getYear(year);
		// if (y == null) {
		// Logger.log("Persister::createDay: year " + year + " for " +
		// employee.getSurname() + " doesn´t exist");
		// return false;
		// }
		//
		// Month m = y.getMonth(month);
		// if (m == null) {
		// Logger.log("Persister::createDay: year " + year + " for " +
		// employee.getSurname() + " doesn´t exist");
		// return false;
		// }

		boolean exist = FileAndFolderManager.existFile(path);
		Month m;
		if (!exist) {
			m = new Month();
			m.setYear(year);
			m.setMonth(month);
		} else {
			m = (Month) new XMLManager().readFile(path, Month.class);
		}

		Day d = new Day();
		d.setDay(day);
		m.addDay(d);

		new XMLManager().writeFile(m, path);

		return true;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setClients(Clients clients) {
		this.clients = clients;
	}

}
