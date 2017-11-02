package gruentausch.wizards.customer;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.Wizard;

public class CreateCustomerWizard extends Wizard {
	
	@Inject
	CreateCustomerPage1 page1;
	
	@Inject
	CreateCustomerPage2 page2;

	@Inject
	MApplication application;
	
	public CreateCustomerWizard() {
		setWindowTitle("Kunden anlegen");
	}

	@Override
	public void addPages() {
		addPage(page1);
		addPage(page2);
	}

	@Override
	public boolean performFinish() {
//		Team team = application.getContext().get(Team.class);
//		Employee employee = page1.getEmployee();
//		team.addEmployee(employee);
//		// TODO implement!
//		Persister.update(employee);
//		File file = new XMLManager().writeFile(team, "data/Kunden.xml");
		return true;
	}

	@Override
	public boolean canFinish() {
		return page1.isPageComplete();
	}

//	@Override
//	public IWizardPage getNextPage(IWizardPage page) {
//		return super.getNextPage(page);
//	}

}
