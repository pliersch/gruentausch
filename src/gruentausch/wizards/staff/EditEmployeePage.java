package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import gruentausch.parts.EmployeePart;

public class EditEmployeePage extends WizardPage {

	private EmployeePart part;

	@Inject
	public EditEmployeePage() {
		super("EditEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		part = new EmployeePart();
		part.createControls(container);
		part.setEditable(true);
		setControl(container);
	}
	
	@Override
	public boolean isPageComplete() {
		return part.canFinish();
	}

}
