package gruentausch.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import gruentausch.pdf.PdfCreator;

public class CreatePdfHandler {
	
	@Execute
	public void execute(Shell shell) {
//		MessageDialog.openInformation(shell, "About", "Eclipse 4 RCP Application");
		new PdfCreator().print();
	}
	
}
