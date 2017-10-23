package gruentausch.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import gruentausch.wizards.staff.CreateEmployeePage;
import gruentausch.wizards.staff.CreateEmployeeWizard;
import gruentausch.wizards.staff.EditEmployeePage;
import gruentausch.wizards.staff.EditEmployeeWizard;

public class NewEmployeeHandler {
	
	@Execute
	public void execute(Shell shell, IEclipseContext ctx) {
		// create new context
		IEclipseContext wizardCtx = ctx.createChild();
		
		// create WizardPages via CIF
		CreateEmployeePage page = ContextInjectionFactory.make(CreateEmployeePage.class, wizardCtx);
		wizardCtx.set(CreateEmployeePage.class, page);
		
		CreateEmployeeWizard wizard = ContextInjectionFactory.make(CreateEmployeeWizard.class, wizardCtx);
		 
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
		
//		// create todo and store in context
//		// use -1 to indicate a not existing id
//		Todo todo = new Todo(-1);
//		todo.setDueDate(new Date());
//		wizardCtx.set(Todo.class, todo);

		// create WizardPages via CIF
//		TodoWizardPage1 page1 = ContextInjectionFactory.make(TodoWizardPage1.class, wizardCtx);
//		wizardCtx.set(TodoWizardPage1.class, page1);
//		// no context needed for the creation
//		TodoWizardPage2 page2 = ContextInjectionFactory.make(TodoWizardPage2.class, null);
//		wizardCtx.set(TodoWizardPage2.class, page2);
//		
//		EditEmployeeWizard wizard = ContextInjectionFactory.make(EditEmployeeWizard.class, wizardCtx);
//		 
//		WizardDialog dialog = new WizardDialog(shell, wizard);
//		dialog.open();

	}
	
	
}
