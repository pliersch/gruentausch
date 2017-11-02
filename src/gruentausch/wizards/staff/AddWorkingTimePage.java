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
import gruentausch.views.ViewDataChangeHandler;
import gruentausch.views.timetable.TimeTable;

public class AddWorkingTimePage extends WizardPage implements ViewDataChangeHandler {

	private Employee employee;
	private TimeTable view;

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
		if (unresolvedWorkingDays.size() == 0) {
			setTitle("Keine offenen Arbeitstage bei/für " + employee.getGivenname() + " " + employee.getSurname());
		} else {
			view = new TimeTable();
			view.createControls(container);
			// view.setEditable(true);
			view.setDataViewHandler(this);
			view.updateTable(unresolvedWorkingDays);
		}
	}

	@Override
	public boolean isPageComplete() {
		return false;
		// return view.fieldsValid();
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
