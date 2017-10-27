package gruentausch.wizards.staff;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.util.WorkingTimeUtil;
import gruentausch.views.EmployeeDataView.IEmployeeDataViewHandler;
import gruentausch.views.timetable.TimeTableView;

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
		List<Day> unresolvedWorkingDays = new WorkingTimeUtil().getUnresolvedWorkingDays(employee);
		setControl(container);
		if(unresolvedWorkingDays.size() == 0) {
			setTitle("Keine offenen Arbeitstage bei/für " + employee.getGivenname() + " " + employee.getSurname());			
		} else {
			view = new TimeTableView();
			view.createControls(container);
//		view.setEditable(true);
//		view.setDataViewHandler(this);
			view.updateTable(unresolvedWorkingDays);
		}
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
