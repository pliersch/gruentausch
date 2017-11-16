package gruentausch.wizards.staff;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.model.Team;
import gruentausch.model.Year;
import gruentausch.util.XMLManager;

public class CreateEmployeeWizard extends Wizard {
	
	@Inject
	CreateEmployeePage page1;

	@Inject
	MApplication application;
	
	public CreateEmployeeWizard() {
		setWindowTitle("Mitarbeiter anlegen");
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		Team team = application.getContext().get(Team.class);
		Employee employee = page1.getEmployee();
		team.addEmployee(employee);

		Calendar calendar = Calendar.getInstance();
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH);
		int d = calendar.get(Calendar.DAY_OF_MONTH);

		Year year = new Year();
		employee.addYear(year);
		year.setYear(y);

		Month month = new Month();
		month.setYear(y);
		month.setMonth(m);
		month.setParent(year);
		year.addMonth(month);

		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Day> days = new ArrayList<Day>();
		for (int l = 0; l < daysInMonth; l++) {
			Day day = new Day();
			day.setDay(l + 1);
			day.setParent(month);
			days.add(day);
			month.setDays(days);
		}

		File file = new XMLManager().writeFile(team, "data/Mitarbeiter.xml");
		// return Persister.getInstance().addEmployee(employee);
		return file != null;
	}

	@Override
	public boolean canFinish() {
		return page1.isPageComplete();
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return super.getNextPage(page);
	}

}
