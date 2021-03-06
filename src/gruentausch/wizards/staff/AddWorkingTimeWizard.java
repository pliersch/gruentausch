package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import gruentausch.model.Team;

public class AddWorkingTimeWizard extends Wizard {

	@Inject
	AddWorkingTimePage page1;
	
	@Inject
	MApplication application;

	@Inject
	public AddWorkingTimeWizard() {
		setWindowTitle("Arbeitszeiten");
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		Team team = application.getContext().get(Team.class);
//		Employee employee = page1.getEmployee();
//		team.updateEmployee(employee);
//		File file = new XMLManager().writeFile(team, "data/Mitarbeiter.xml");
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
