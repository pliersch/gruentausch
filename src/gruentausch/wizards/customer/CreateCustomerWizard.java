package gruentausch.wizards.customer;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.wizard.Wizard;

import gruentausch.model.Clients;
import gruentausch.model.Customer;
import gruentausch.util.XMLManager;

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
		Clients clients = application.getContext().get(Clients.class);
		if (clients == null) {
			clients = new Clients();
		}
		Customer customer = page1.getCustomer();
		clients.addCustomer(customer);
		// // TODO implement!
		// Persister.update(employee);
		File file = new XMLManager().writeFile(clients, "data/Kunden.xml");
		return true;
	}

	@Override
	public boolean canFinish() {
		return page1.isPageComplete();
	}

	// @Override
	// public IWizardPage getNextPage(IWizardPage page) {
	// return super.getNextPage(page);
	// }

}
