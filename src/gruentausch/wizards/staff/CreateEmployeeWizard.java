package gruentausch.wizards.staff;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import gruentausch.model.Employee;
import gruentausch.model.Team;
import gruentausch.util.XMLManager;

public class CreateEmployeeWizard extends Wizard {

	boolean finish = false;
	
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
		File file = new XMLManager().writeFile(team, "data/Employee.xml");
		return true;
	}

	@Override
	public boolean canFinish() {
		return page1.canFinish();
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return super.getNextPage(page);
	}

}