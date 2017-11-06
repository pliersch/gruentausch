package gruentausch.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import gruentausch.pdf.PdfCreator;
import gruentausch.util.Logger;

public class CreatePdfHandler {
	
	// @Inject Logger logger;
	
	@Execute
	public void execute(Shell shell) {
		new PdfCreator().print();
		Logger.log("PDF created");
	}
	
}
