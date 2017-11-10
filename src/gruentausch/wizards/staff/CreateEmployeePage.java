package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Adress;
import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView;
import gruentausch.views.ViewDataChangeHandler;

public class CreateEmployeePage extends WizardPage implements ViewDataChangeHandler {

	private EmployeeDataView view;

	@Inject
	public CreateEmployeePage() {
		super("CreateEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	@Override
	public void createControl(Composite parent) {
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
		Adress adress = new Adress();
		employee.setGivenname(view.txtGivenname.getText());
		employee.setSurname(view.txtSurname.getText());
		adress.setCity(view.txtCity.getText());
		adress.setPlz(Integer.valueOf(view.txtPLZ.getText()));
		adress.setStreet(view.txtStreet.getText());
		employee.setAdress(adress);
		return employee;
	}

	@Override
	public void handleDataChange(Object object) {
		getWizard().getContainer().updateButtons();
	}
}
