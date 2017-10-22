package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import gruentausch.views.EmployeeDataView;

public class EditEmployeePage extends WizardPage {

	@Inject
	public EditEmployeePage() {
		super("page1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		// we could also create this class via DI but
		// in this example we stay with the next operator
		EmployeeDataView part = new EmployeeDataView();
		part.createControls(container);
		setControl(container);
	}

}
