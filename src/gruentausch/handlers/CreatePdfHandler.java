package gruentausch.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.e4.core.services;

import gruentausch.parts.LoggingPart;
import gruentausch.pdf.PdfCreator;

public class CreatePdfHandler {
	
	@Inject Logger logger;
	
	@Execute
	public void execute(Shell shell) {
		new PdfCreator().print();
		LoggingPart.log("PDF created");
	}
	
}
