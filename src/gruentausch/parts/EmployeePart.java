package gruentausch.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Employee;
import gruentausch.util.RegExUtil;

public class EmployeePart {

	private Text _txtStreet;
	private Text _txtSurname;
	private Text _txtGivenname;
	private Text _txtPLZ;
	private Text _txtCity;
	
	private boolean _isValidStreet;
	private boolean _isValidSurname;
	private boolean _isValidGivenname;
	private boolean _isValidPLZ;
	private boolean _isValidCity;
	
	private ControlDecoration _decoratorStreet;
	private ControlDecoration _decoratorSurname;
	private ControlDecoration _decoratorGivenname;
	private ControlDecoration _decoratorPLZ;
	private ControlDecoration _decoratorCity;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.horizontalSpacing = 8;
		container.setLayout(gl_container);
		WizardFocusListener wizardFocusListener = new WizardFocusListener();
		
		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Name:");
		}
		{
			_txtSurname = new Text(container, SWT.BORDER);
			_txtSurname.addFocusListener(wizardFocusListener);
			_txtSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			_txtSurname.setEditable(false);
		}
		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Vorname:");
		}
		{
			_txtGivenname = new Text(container, SWT.BORDER);
			_txtGivenname.addFocusListener(wizardFocusListener);
			_txtGivenname.setEditable(false);
			_txtGivenname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Label lblStrae = new Label(container, SWT.NONE);
			lblStrae.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblStrae.setText("Stra\u00DFe:");
		}
		{
			_txtStreet = new Text(container, SWT.BORDER);
			_txtStreet.addFocusListener(wizardFocusListener);
			_txtStreet.setEditable(false);
			_txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		}
		{
			Label lblPlzOrt = new Label(container, SWT.NONE);
			lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPlzOrt.setText("PLZ:");
		}
		{
			_txtPLZ = new Text(container, SWT.BORDER);
			_txtPLZ.addFocusListener(wizardFocusListener);
			_txtPLZ.setEditable(false);
			GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_text_2.widthHint = 206;
			_txtPLZ.setLayoutData(gd_text_2);
		}
		{
			Label lblPlzOrt = new Label(container, SWT.NONE);
			lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPlzOrt.setText("Ort:");
		}
		{
			_txtCity = new Text(container, SWT.BORDER);
			_txtCity.addFocusListener(wizardFocusListener);
			_txtCity.setEditable(false);
			_txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			{
				String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum", "Goyatz", "Sauen" };
				new AutoCompleteField(_txtCity, new TextContentAdapter(), cities);
			}
		}
	}
	
	@Inject
	void updateEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			_txtGivenname.setText(employee.getGivenname());
			_txtSurname.setText(employee.getSurname());
		}
	}

	public void setEditable(boolean b) {
		_txtSurname.setEditable(true);
		_txtGivenname.setEditable(true);
		_txtStreet.setEditable(true);
		_txtPLZ.setEditable(true);
		_txtCity.setEditable(true);
		
	}

	public boolean canFinish() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}
	
	private void showWarning(ControlDecoration decoration) {
		//decoration = new ControlDecoration(target, SWT.LEFT | SWT.TOP);
		decoration.setDescriptionText("Bitte prüfen");
		Image errorImage = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
		decoration.setImage(errorImage);
	}

	private void hideWarning(ControlDecoration decoration) {
		if (decoration != null) {
			decoration.hide();
			decoration.dispose();
		}
	}
	
	class WizardFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			Text source = (Text) e.getSource();
			if (source.equals(_txtCity)) {
				_isValidCity = RegExUtil.validateName(_txtCity.getText());
				if (!_isValidCity) {
					if ( _decoratorCity == null) {
						_decoratorCity = new ControlDecoration(_txtCity, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorCity);						
					}
				} else {
					hideWarning(_decoratorCity);
					_decoratorCity = null;
				}
			}
			else if (source.equals(_txtPLZ)) {
				_isValidPLZ = RegExUtil.validatePLZ(_txtPLZ.getText());
				if (!_isValidPLZ) {
					if ( _decoratorPLZ == null) {
						_decoratorPLZ = new ControlDecoration(_txtPLZ, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorPLZ);						
					}
				} else {
					hideWarning(_decoratorPLZ);
					_decoratorPLZ = null;
				}
			}
			else if (source.equals(_txtStreet)) {
				_isValidStreet = RegExUtil.validateName(_txtStreet.getText());
				if (!_isValidStreet) {
					if ( _decoratorStreet == null) {
						_decoratorStreet = new ControlDecoration(_txtStreet, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorStreet);						
					}
				} else {
					hideWarning(_decoratorStreet);
					_decoratorStreet = null;
				}
			}
			else if (source.equals(_txtGivenname)) {
				_isValidGivenname = RegExUtil.validateName(_txtGivenname.getText());
				if (!_isValidGivenname) {
					if ( _decoratorGivenname == null) {
						_decoratorGivenname = new ControlDecoration(_txtGivenname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorGivenname);						
					}
				} else {
					hideWarning(_decoratorGivenname);
					_decoratorGivenname = null;
				}
			}
			else if (source.equals(_txtSurname)) {
				_isValidSurname = RegExUtil.validateName(_txtSurname.getText());
				if (!_isValidSurname) {
					if ( _decoratorSurname == null) {
						_decoratorSurname = new ControlDecoration(_txtSurname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorSurname);						
					}
				} else {
					hideWarning(_decoratorSurname);
					_decoratorSurname = null;
				}
			}
		}
		
	}

}
