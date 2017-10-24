package gruentausch.parts;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView;

public class EmployeePart extends EmployeeDataView {
		
	@Inject
	void setEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			updateEmployee(employee);
		}
	}	
}
