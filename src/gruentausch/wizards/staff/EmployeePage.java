package gruentausch.wizards.staff;

import javax.inject.Inject;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Employee;
import gruentausch.util.RegExUtil;

public class EmployeePage extends WizardPage {

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
	private Employee employee;

	@Inject
	public EmployeePage() {
		super("CreateEmployeePage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.horizontalSpacing = 8;
		container.setLayout(gl_container);
		WizardKeyListener keyListener = new WizardKeyListener();
		setControl(container);

		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Name:");
		}
		{
			_txtSurname = new Text(container, SWT.BORDER);
			_txtSurname.addKeyListener(keyListener);
			_txtSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Vorname:");
		}
		{
			_txtGivenname = new Text(container, SWT.BORDER);
			_txtGivenname.addKeyListener(keyListener);
			_txtGivenname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Label lblStrae = new Label(container, SWT.NONE);
			lblStrae.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblStrae.setText("Stra\u00DFe:");
		}
		{
			_txtStreet = new Text(container, SWT.BORDER);
			_txtStreet.addKeyListener(keyListener);
			_txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		}
		{
			Label lblPlzOrt = new Label(container, SWT.NONE);
			lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPlzOrt.setText("PLZ:");
		}
		{
			_txtPLZ = new Text(container, SWT.BORDER);
			_txtPLZ.addKeyListener(keyListener);
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
			_txtCity.addKeyListener(keyListener);
			_txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			{
				String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum", "Goyatz", "Sauen" };
				new AutoCompleteField(_txtCity, new TextContentAdapter(), cities);
			}
		}
		if (employee != null) {
			_txtSurname.setText(employee.getSurname());
			_txtGivenname.setText(employee.getGivenname());
			_isValidGivenname = true;
			_isValidSurname = true;
		}
	}

	public boolean canFinish() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}

	@Override
	public boolean isPageComplete() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}
	
	public Employee getEmployee() {
		employee = new Employee();
		employee.setGivenname(_txtGivenname.getText());
		employee.setSurname(_txtSurname.getText());
//		employee.setGivenname(_txtGivenname.getText());
//		employee.setGivenname(_txtGivenname.getText());
//		employee.setGivenname(_txtGivenname.getText());
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee; 
	}

	private void showWarning(ControlDecoration decoration) {
		// decoration = new ControlDecoration(target, SWT.LEFT | SWT.TOP);
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
	
	class WizardKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			Text source = (Text) e.getSource();
			if (source.equals(_txtCity)) {
				_isValidCity = RegExUtil.validateName(_txtCity.getText());
				if (!_isValidCity) {
					if (_decoratorCity == null) {
						_decoratorCity = new ControlDecoration(_txtCity, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorCity);
					}
				} else {
					hideWarning(_decoratorCity);
					_decoratorCity = null;
				}
			} else if (source.equals(_txtPLZ)) {
				_isValidPLZ = RegExUtil.validatePLZ(_txtPLZ.getText());
				if (!_isValidPLZ) {
					if (_decoratorPLZ == null) {
						_decoratorPLZ = new ControlDecoration(_txtPLZ, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorPLZ);
					}
				} else {
					hideWarning(_decoratorPLZ);
					_decoratorPLZ = null;
				}
			} else if (source.equals(_txtStreet)) {
				_isValidStreet = RegExUtil.validateName(_txtStreet.getText());
				if (!_isValidStreet) {
					if (_decoratorStreet == null) {
						_decoratorStreet = new ControlDecoration(_txtStreet, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorStreet);
					}
				} else {
					hideWarning(_decoratorStreet);
					_decoratorStreet = null;
				}
			} else if (source.equals(_txtGivenname)) {
				_isValidGivenname = RegExUtil.validateName(_txtGivenname.getText());
				if (!_isValidGivenname) {
					if (_decoratorGivenname == null) {
						_decoratorGivenname = new ControlDecoration(_txtGivenname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorGivenname);
					}
				} else {
					hideWarning(_decoratorGivenname);
					_decoratorGivenname = null;
				}
			} else if (source.equals(_txtSurname)) {
				_isValidSurname = RegExUtil.validateName(_txtSurname.getText());
				if (!_isValidSurname) {
					if (_decoratorSurname == null) {
						_decoratorSurname = new ControlDecoration(_txtSurname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorSurname);
					}
				} else {
					hideWarning(_decoratorSurname);
					_decoratorSurname = null;
				}
			}
			getWizard().getContainer().updateButtons();
		}
	}	
}
