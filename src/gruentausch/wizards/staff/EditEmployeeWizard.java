package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class EditEmployeeWizard extends Wizard {

	boolean finish = false;

	@Inject
	EditEmployeePage page1;

	@Inject
	public EditEmployeeWizard() {
		setWindowTitle("Bearbeite Mitarbeiter");
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
		return finish;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return super.getNextPage(page);
	}

}
