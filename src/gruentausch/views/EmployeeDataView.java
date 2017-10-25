package gruentausch.views;

import javax.annotation.PostConstruct;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
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

public class EmployeeDataView {
	
	public interface IEmployeeDataViewHandler {
		void handleDataChange();
	}

	public Text txtStreet;
	public Text txtSurname;
	public Text txtGivenname;
	public Text txtPLZ;
	public Text txtCity;

	protected boolean _isValidStreet;
	protected boolean _isValidSurname;
	protected boolean _isValidGivenname;
	protected boolean _isValidPLZ;
	protected boolean _isValidCity;

	protected ControlDecoration _decoratorStreet;
	protected ControlDecoration _decoratorSurname;
	protected ControlDecoration _decoratorGivenname;
	protected ControlDecoration _decoratorPLZ;
	protected ControlDecoration _decoratorCity;
	private IEmployeeDataViewHandler _handler;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.horizontalSpacing = 8;
		container.setLayout(gl_container);
		WizardKeyListener keyListener = new WizardKeyListener();

		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Name:");
		}
		{
			txtSurname = new Text(container, SWT.BORDER);
			txtSurname.addKeyListener(keyListener);
			txtSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			txtSurname.setEditable(false);
		}
		{
			Label lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText("Vorname:");
		}
		{
			txtGivenname = new Text(container, SWT.BORDER);
			txtGivenname.addKeyListener(keyListener);
			txtGivenname.setEditable(false);
			txtGivenname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Label lblStrae = new Label(container, SWT.NONE);
			lblStrae.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblStrae.setText("Stra\u00DFe:");
		}
		{
			txtStreet = new Text(container, SWT.BORDER);
			txtStreet.addKeyListener(keyListener);
			txtStreet.setEditable(false);
			txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		}
		{
			Label lblPlzOrt = new Label(container, SWT.NONE);
			lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPlzOrt.setText("PLZ:");
		}
		{
			txtPLZ = new Text(container, SWT.BORDER);
			txtPLZ.addKeyListener(keyListener);
			txtPLZ.setEditable(false);
			GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_text_2.widthHint = 206;
			txtPLZ.setLayoutData(gd_text_2);
		}
		{
			Label lblPlzOrt = new Label(container, SWT.NONE);
			lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPlzOrt.setText("Ort:");
		}
		{
			txtCity = new Text(container, SWT.BORDER);
			txtCity.addKeyListener(keyListener);
			txtCity.setEditable(false);
			txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			{
				String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum", "Goyatz", "Sauen" };
				new AutoCompleteField(txtCity, new TextContentAdapter(), cities);
			}
		}
	}
	
	public void setDataViewHandler(IEmployeeDataViewHandler handler) {
		_handler = handler;
	}

	public void setEditable(boolean b) {
		txtSurname.setEditable(true);
		txtGivenname.setEditable(true);
		txtStreet.setEditable(true);
		txtPLZ.setEditable(true);
		txtCity.setEditable(true);
	}

	public boolean fieldsValid() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}

	private void showWarning(ControlDecoration decoration) {
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

	public void updateEmployee(Employee employee) {
		if (employee.getGivenname() != null) {
			txtGivenname.setText(employee.getGivenname());
			_isValidGivenname = true;
		} else {
			txtGivenname.setText("");
			_isValidGivenname = false;
		}
		if (employee.getSurname() != null) {
			_isValidSurname = true;
			txtSurname.setText(employee.getSurname());
		} else {
			_isValidSurname = false;
			txtSurname.setText("");
		}
		if (employee.getCity() != null) {
			_isValidCity = true;
			txtCity.setText(employee.getCity());
		} else {
			_isValidCity = false;
			txtCity.setText("");
		}
		if (employee.getPlz() != 0) {
			_isValidPLZ = true;
			txtPLZ.setText(Integer.toString(employee.getPlz()));
		} else {
			_isValidPLZ = false;
			txtPLZ.setText("");
		}
		if (employee.getStreet() != null) {
			txtStreet.setText(employee.getStreet());
			_isValidStreet = true;
		} else {
			txtStreet.setText("");
			_isValidStreet = false;
		}
	}

	class WizardKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {}

		public void keyReleased(KeyEvent e) {
			Text source = (Text) e.getSource();
			if (source.equals(txtCity)) {
				_isValidCity = RegExUtil.validateName(txtCity.getText());
				if (!_isValidCity) {
					if (_decoratorCity == null) {
						_decoratorCity = new ControlDecoration(txtCity, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorCity);
					}
				} else {
					hideWarning(_decoratorCity);
					_decoratorCity = null;
				}
			} else if (source.equals(txtPLZ)) {
				_isValidPLZ = RegExUtil.validatePLZ(txtPLZ.getText());
				if (!_isValidPLZ) {
					if (_decoratorPLZ == null) {
						_decoratorPLZ = new ControlDecoration(txtPLZ, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorPLZ);
					}
				} else {
					hideWarning(_decoratorPLZ);
					_decoratorPLZ = null;
				}
			} else if (source.equals(txtStreet)) {
				_isValidStreet = RegExUtil.validateName(txtStreet.getText());
				if (!_isValidStreet) {
					if (_decoratorStreet == null) {
						_decoratorStreet = new ControlDecoration(txtStreet, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorStreet);
					}
				} else {
					hideWarning(_decoratorStreet);
					_decoratorStreet = null;
				}
			} else if (source.equals(txtGivenname)) {
				_isValidGivenname = RegExUtil.validateName(txtGivenname.getText());
				if (!_isValidGivenname) {
					if (_decoratorGivenname == null) {
						_decoratorGivenname = new ControlDecoration(txtGivenname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorGivenname);
					}
				} else {
					hideWarning(_decoratorGivenname);
					_decoratorGivenname = null;
				}
			} else if (source.equals(txtSurname)) {
				_isValidSurname = RegExUtil.validateName(txtSurname.getText());
				if (!_isValidSurname) {
					if (_decoratorSurname == null) {
						_decoratorSurname = new ControlDecoration(txtSurname, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorSurname);
					}
				} else {
					hideWarning(_decoratorSurname);
					_decoratorSurname = null;
				}
			}_handler.handleDataChange();
		}
	}
}
