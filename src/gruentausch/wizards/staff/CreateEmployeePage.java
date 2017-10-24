package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView;
import gruentausch.views.EmployeeDataView.IEmployeeDataViewHandler;

public class CreateEmployeePage extends WizardPage implements IEmployeeDataViewHandler {

	private EmployeeDataView view;

	@Inject
	public CreateEmployeePage() {
		super("CreateEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		view = new EmployeeDataView();
		view.createControls(container);
		view.setEditable(true);
		view.setDataViewHandler(this);
		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return view.fieldsValid();
	}

	public Employee getEmployee() {
		Employee employee = new Employee();
		employee.setGivenname(view.txtGivenname.getText());
		employee.setSurname(view.txtSurname.getText());
		employee.setCity(view.txtCity.getText());
		employee.setPlz(Integer.valueOf(view.txtPLZ.getText()));
		employee.setStreet(view.txtStreet.getText());
		return employee;
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
