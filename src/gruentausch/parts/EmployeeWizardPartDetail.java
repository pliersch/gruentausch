package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EmployeeWizardPartDetail {
	
	private Text txtSummary;
	private Text txtDescription;
	private Button btnDone;
	private DateTime dateTime;
	private Label lblDescription;

	@PostConstruct
	public void createControls(Composite parent) {

		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.marginRight = 10;
		gl_parent.marginLeft = 10;
		gl_parent.horizontalSpacing = 10;
		gl_parent.marginWidth = 0;
		parent.setLayout(gl_parent);

		new Label(parent, SWT.NONE);

		txtSummary = new Text(parent, SWT.BORDER);
		txtSummary
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setText("Description");

		txtDescription = new Text(parent, SWT.BORDER | SWT.MULTI);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 122;
		txtDescription.setLayoutData(gd);

		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setText("Due Date");

		dateTime = new DateTime(parent, SWT.BORDER);
		dateTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(parent, SWT.NONE);

		btnDone = new Button(parent, SWT.CHECK);
		btnDone.setText("Done");

	}

	@Focus
	public void onFocus() {
		// The following assumes that you have a Text field
		// called summary
		txtSummary.setFocus();
	}
	
}
