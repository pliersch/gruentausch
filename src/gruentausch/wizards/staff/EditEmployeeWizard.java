package gruentausch.wizards.staff;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import gruentausch.model.Person;
import gruentausch.model.Team;
import gruentausch.util.XMLManager;

public class EditEmployeeWizard extends Wizard {

	@Inject
	EditEmployeePage page1;
	
	@Inject
	MApplication application;

	@Inject
	public EditEmployeeWizard() {
		setWindowTitle("Mitarbeiter bearbeiten");
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		Team team = application.getContext().get(Team.class);
		Person employee = page1.getEmployee();
		team.updateEmployee(employee);
		File file = new XMLManager().writeFile(team, "data/Mitarbeiter.xml");
		return true;
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
