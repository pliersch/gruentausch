package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView.IEmployeeDataViewHandler;
import gruentausch.views.TimeTableView;

public class AddWorkingTimePage extends WizardPage implements IEmployeeDataViewHandler {

	private Employee employee;
	private TimeTableView view;

	@Inject
	public AddWorkingTimePage() {
		super("EditEmployeePage1");
		setTitle("der titel");
		setDescription("offene Arbeitszeiten");
	}
	
	@Override
	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		view = new TimeTableView();
		view.createControls(container);
//		view.setEditable(true);
//		view.updateTable(new WorkingTimeUtil().getUnresolvedWorkingDays(employee));
//		view.setDataViewHandler(this);
		setControl(container);
		setTitle(employee.getGivenname() + " " + employee.getSurname());
	}

	@Override
	public boolean isPageComplete() {
		return false;
//		return view.fieldsValid();
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
