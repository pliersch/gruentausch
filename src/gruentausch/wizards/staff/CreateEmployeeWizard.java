package gruentausch.wizards.staff;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

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

		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DAY_OF_MONTH);

		Year year = new Year();
		employee.addYear(year);
		year.setYear(y);

		Month month = new Month();
		month.setYear(y);
		month.setMonth(m + 1);
		year.addMonth(month);

		// Day day = new Day();
		// day.setDay(d);
		// month.addDay(day);

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
