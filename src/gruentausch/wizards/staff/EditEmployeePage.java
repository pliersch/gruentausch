package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Adress;
import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView;
import gruentausch.views.EmployeeDataView.IEmployeeDataViewHandler;

public class EditEmployeePage extends WizardPage implements IEmployeeDataViewHandler {

	private Employee employee;
	private EmployeeDataView view;

	@Inject
	public EditEmployeePage() {
		super("EditEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	@Override
	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		view = new EmployeeDataView();
		view.createControls(container);
		view.setEditable(true);
		view.updateEmployee(employee);
		view.setDataViewHandler(this);
		setControl(container);
	}

	@Override
	public boolean isPageComplete() {
		return view.fieldsValid();
	}

	public Employee getEmployee() {
		employee.setGivenname(view.txtGivenname.getText());
		employee.setSurname(view.txtSurname.getText());
		Adress adress = new Adress();
		adress.setCity(view.txtCity.getText());
		adress.setPlz(Integer.valueOf(view.txtPLZ.getText()));
		adress.setStreet(view.txtStreet.getText());
		employee.setAdress(adress);
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
