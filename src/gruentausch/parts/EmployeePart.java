package gruentausch.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Composite;

import gruentausch.model.Employee;
import gruentausch.model.Team;
import gruentausch.views.EmployeeDataView;

public class EmployeePart extends EmployeeDataView {
	
	
	@PostConstruct
	public void createControls(Composite parent, MApplication application) {
		IEclipseContext context = application.getContext();
		Team team = (Team) context.get(Team.class);
		team.addPropertyChangeListener("employee", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateEmployee((Employee) evt.getNewValue());
			}
		});
	}
		
	@Inject
	void setEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			updateEmployee(employee);
		}
	}	
}
