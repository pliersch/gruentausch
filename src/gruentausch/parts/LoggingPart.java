package gruentausch.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import gruentausch.util.Logger;

public class LoggingPart {
	
	private String messages = "";
	
	private Text text;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		messages = Logger.getFullLogAsString();
		text.setText(messages);
		
		Logger.addPropertyChangeListener("logging", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object newValue = evt.getNewValue();
				System.out.println(newValue);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						messages += (newValue + "\n");
						text.setText(messages);
					}
				});
			}
		});
	}

}
