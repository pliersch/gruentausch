package gruentausch.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.wizards.staff.EditEmployeePage;
import gruentausch.wizards.staff.EditEmployeeWizard;

public class EditEmployeeHandler {
	
	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();

		// create WizardPages via CIF
		EditEmployeePage page = ContextInjectionFactory.make(EditEmployeePage.class, wizardCtx);
		wizardCtx.set(EditEmployeePage.class, page);
		
		EditEmployeeWizard wizard = ContextInjectionFactory.make(EditEmployeeWizard.class, wizardCtx);
		 
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();

	}
}
