package gruentausch.wizards.customer;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Adress;
import gruentausch.model.Employee;
import gruentausch.views.CustomerDataView;
import gruentausch.views.ViewDataChangeHandler;

public class CreateCustomerPage1 extends WizardPage implements ViewDataChangeHandler {

	private CustomerDataView view;
	
	public Text txtStreet;
	public Text txtName;
	public Text txtPLZ;
	public Text txtCity;


	@Inject
	public CreateCustomerPage1() {
		super("CreateCustomerPage1");
		setTitle("der titel");
		setDescription("die beschreibung");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		view = new CustomerDataView();
		view.createControls(container);
		view.setEditable(true);
		view.setDataViewHandler(this);
		setControl(container);
	
		
//		final Composite rootComposite = new Composite(parent, SWT.NONE);
//		rootComposite.setLayout(GridLayoutFactory.fillDefaults().create());
//		
//		final ScrolledComposite sc = new ScrolledComposite(rootComposite, SWT.BORDER | SWT.V_SCROLL);
//		sc.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 200).create());
//		sc.setExpandHorizontal(true);
//		sc.setExpandVertical(true);
//		
//		final Composite containerMain = new Composite(sc, SWT.NULL);
//		containerMain.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());
//		
//		for (int i = 0; i < 50; i++) {
//			final Label label = new Label(containerMain, SWT.NONE);
//			label.setText("Label " + i + 1);
//		}
//		
//		sc.setContent(containerMain);
//		sc.setMinSize(containerMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		
//		setControl(rootComposite);

	}

	@Override
	public boolean isPageComplete() {
		return true;
//		return view.fieldsValid();
	}

	public Employee getEmployee() {
		Employee employee = new Employee();
		Adress adress = new Adress();
		employee.setSurname(view.txtName.getText());
		adress.setCity(view.txtCity.getText());
		adress.setPlz(Integer.valueOf(view.txtPLZ.getText()));
		adress.setStreet(view.txtStreet.getText());
		employee.setAdress(adress);
		return employee;
	}

	@Override
	public void handleDataChange() {
		getWizard().getContainer().updateButtons();
	}
}
