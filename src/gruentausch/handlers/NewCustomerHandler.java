package gruentausch.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.wizards.customer.CreateCustomerPage;
import gruentausch.wizards.customer.CreateCustomerWizard;

public class NewCustomerHandler {

	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();

		CreateCustomerPage page = ContextInjectionFactory.make(CreateCustomerPage.class, wizardCtx);
		wizardCtx.set(CreateCustomerPage.class, page);

		CreateCustomerWizard wizard = ContextInjectionFactory.make(CreateCustomerWizard.class, wizardCtx);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}
}
