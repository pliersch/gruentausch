package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class CreateEmployeeWizard extends Wizard {

	boolean finish = false;

	@Inject
	CreateEmployeePage page1;

	@Inject
	public CreateEmployeeWizard() {
		setWindowTitle("Mitarbeiter anlegen");
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
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
