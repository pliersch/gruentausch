package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class LoggingPart {
	
	public static String messages = "";
	

	public static void log(String message) {

		messages += (message + "\n");
	}
	
	private Text text;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		text.setText(messages);
	}

}
