package gruentausch.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.wizards.customer.CreateCustomerPage1;
import gruentausch.wizards.customer.CreateCustomerPage2;
import gruentausch.wizards.customer.CreateCustomerWizard;

public class NewCustomerHandler {

	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();

		CreateCustomerPage1 page1 = ContextInjectionFactory.make(CreateCustomerPage1.class, wizardCtx);
		wizardCtx.set(CreateCustomerPage1.class, page1);
		
		CreateCustomerPage2 page2 = ContextInjectionFactory.make(CreateCustomerPage2.class, wizardCtx);
		wizardCtx.set(CreateCustomerPage2.class, page2);

		CreateCustomerWizard wizard = ContextInjectionFactory.make(CreateCustomerWizard.class, wizardCtx);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}
}
