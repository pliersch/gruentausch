package gruentausch.views;

import javax.annotation.PostConstruct;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Adress;
import gruentausch.model.Employee;
import gruentausch.util.RegExUtil;

public class SampleDataViewWithTableBottom {

	public Text txtStreet;
	public Text txtName;
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
	private ViewDataChangeHandler _handler;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gl_container = new GridLayout(4, false);
		gl_container.horizontalSpacing = 8;
		container.setLayout(gl_container);
		WizardKeyListener keyListener = new WizardKeyListener();

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		
		txtName = new Text(container, SWT.BORDER);
		txtName.addKeyListener(keyListener);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtName.setEditable(false);
		
		Label lblStrae = new Label(container, SWT.NONE);
		lblStrae.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStrae.setText("Stra\u00DFe:");
		
		txtStreet = new Text(container, SWT.BORDER);
		txtStreet.addKeyListener(keyListener);
		txtStreet.setEditable(false);
		txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblPlz = new Label(container, SWT.NONE);
		lblPlz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPlz.setText("PLZ:");
		
		txtPLZ = new Text(container, SWT.BORDER);
		txtPLZ.addKeyListener(keyListener);
		txtPLZ.setEditable(false);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_2.widthHint = 206;
		txtPLZ.setLayoutData(gd_text_2);
		
		Label lblOrt = new Label(container, SWT.NONE);
		lblOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrt.setText("Ort:");
		
		txtCity = new Text(container, SWT.BORDER);
		txtCity.addKeyListener(keyListener);
		txtCity.setEditable(false);
		txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum", "Goyatz", "Sauen" };
		new AutoCompleteField(txtCity, new TextContentAdapter(), cities);
		
		TableViewer tableViewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		//tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		
		
	}

	public void setDataViewHandler(ViewDataChangeHandler handler) {
		_handler = handler;
	}

	public void setEditable(boolean b) {
		txtName.setEditable(true);
		txtStreet.setEditable(true);
		txtPLZ.setEditable(true);
		txtCity.setEditable(true);
	}

	public boolean fieldsValid() {
		return _isValidCity && _isValidGivenname && _isValidPLZ && _isValidStreet && _isValidSurname;
	}

	private void showWarning(ControlDecoration decoration) {
		decoration.setDescriptionText("Bitte pr�fen");
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
		if (employee == null) {
			clearUI();
		} else {
			updateUI(employee);
		}
	}

	private void updateUI(Employee employee) {
		Adress adress = employee.getAdress();
		if (employee.getGivenname() != null) {
			txtGivenname.setText(employee.getGivenname());
			_isValidGivenname = true;
		} else {
			txtGivenname.setText("");
			_isValidGivenname = false;
		}
		if (employee.getSurname() != null) {
			_isValidSurname = true;
			txtName.setText(employee.getSurname());
		} else {
			_isValidSurname = false;
			txtName.setText("");
		}
		if (adress.getCity() != null) {
			_isValidCity = true;
			txtCity.setText(adress.getCity());
		} else {
			_isValidCity = false;
			txtCity.setText("");
		}
		if (adress.getPlz() != 0) {
			_isValidPLZ = true;
			txtPLZ.setText(Integer.toString(adress.getPlz()));
		} else {
			_isValidPLZ = false;
			txtPLZ.setText("");
		}
		if (adress.getStreet() != null) {
			txtStreet.setText(adress.getStreet());
			_isValidStreet = true;
		} else {
			txtStreet.setText("");
			_isValidStreet = false;
		}
	}

	private void clearUI() {
		txtGivenname.setText("");
		txtName.setText("");
		txtCity.setText("");
		txtPLZ.setText("");
		txtStreet.setText("");
	}

	class WizardKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
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
			} else if (source.equals(txtName)) {
				_isValidSurname = RegExUtil.validateName(txtName.getText());
				if (!_isValidSurname) {
					if (_decoratorSurname == null) {
						_decoratorSurname = new ControlDecoration(txtName, SWT.LEFT | SWT.TOP);
						showWarning(_decoratorSurname);
					}
				} else {
					hideWarning(_decoratorSurname);
					_decoratorSurname = null;
				}
			}
			_handler.handleDataChange(null);
		}
	}
}
