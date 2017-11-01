package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Employee;
import gruentausch.model.Person;
import gruentausch.views.EmployeeDataView;

public class DeleteEmployeePage extends WizardPage {

	private Employee employee;
	private EmployeeDataView view;

	@Inject
	public DeleteEmployeePage() {
		super("DeleteEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		view = new EmployeeDataView();
		view.createControls(container);
		view.setEditable(false);
		view.updateEmployee(employee);
		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return true;
	}

	public Person getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
