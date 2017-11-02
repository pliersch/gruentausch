package gruentausch.wizards.customer;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import gruentausch.views.ViewDataChangeHandler;
import gruentausch.views.timetable.CustomerContactsTable;

public class CreateCustomerPage2 extends WizardPage implements ViewDataChangeHandler {

	private CustomerContactsTable view;

	@Inject
	public CreateCustomerPage2() {
		super("CreateCustomerPage2");
		setTitle("der titel 2");
		setDescription("die beschreibung 2");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		view = new CustomerContactsTable();
		view.createControls(container);
//		view.setEditable(true);
		view.setDataViewHandler(this);
		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return true;
//		return view.fieldsValid();
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
