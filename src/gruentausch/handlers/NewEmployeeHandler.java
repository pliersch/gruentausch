package gruentausch.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.wizards.staff.EmployeePage;
import gruentausch.wizards.staff.CreateEmployeeWizard;

public class NewEmployeeHandler {

	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();

		EmployeePage page = ContextInjectionFactory.make(EmployeePage.class, wizardCtx);
		wizardCtx.set(EmployeePage.class, page);

		CreateEmployeeWizard wizard = ContextInjectionFactory.make(CreateEmployeeWizard.class, wizardCtx);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}
}
