package gruentausch.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.model.Employee;
import gruentausch.wizards.staff.AddWorkingTimePage;
import gruentausch.wizards.staff.AddWorkingTimeWizard;

public class AddWorkingTimeHandler {
	
	private Employee employee;

	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();

		AddWorkingTimePage page = ContextInjectionFactory.make(AddWorkingTimePage.class, wizardCtx);
		page.setEmployee(employee);
		wizardCtx.set(AddWorkingTimePage.class, page);
		
		AddWorkingTimeWizard wizard = ContextInjectionFactory.make(AddWorkingTimeWizard.class, wizardCtx);
		 
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();

	}
	
	@Inject
	void setEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		this.employee = employee;
	}
}
