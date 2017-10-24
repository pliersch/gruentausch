package gruentausch.parts;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

import gruentausch.model.Employee;
import gruentausch.views.EmployeeDataView;

public class EmployeePart extends EmployeeDataView {
		
	@Inject
	void updateEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			if (employee.getGivenname() != null) {
				_txtGivenname.setText(employee.getGivenname());
				_isValidGivenname = true;
			} else {
				_txtGivenname.setText("");
			}
			if (employee.getSurname() != null) {
				_isValidSurname = true;
				_txtSurname.setText(employee.getSurname());
			} else {
				_txtSurname.setText("");
			}
			if (employee.getCity() != null) {
				_isValidCity = true;
				_txtCity.setText(employee.getCity());
			} else {
				_txtCity.setText("");
			}
			if (employee.getPlz() != 0) {
				_isValidPLZ = true;
				_txtPLZ.setText(Integer.toString(employee.getPlz()));
			} else {
				_txtPLZ.setText("");
			}
			if (employee.getStreet() != null) {
				_txtStreet.setText(employee.getStreet());
				_isValidStreet = true;
			} else {
				_txtStreet.setText("");
			}
		}
	}

	public boolean canFinish() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}
	
	
}
