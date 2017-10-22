package gruentausch.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Employee;

public class EmployeeDataView {

	private Text _txtStreet;
	private Text _txtSurname;
	private Text _txtGivenname;
	private Text _txtPLZ;
	private Text _txtCity;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
				
		Group group = new Group(parent, SWT.BORDER);
		group.setText("a text");
		group.setLayout(new GridLayout(4, false));
		{			
			{
				Label lblName = new Label(group, SWT.NONE);
				lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblName.setText("Name:");
			}
			
			{
				_txtSurname = new Text(group, SWT.BORDER);
				_txtSurname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				_txtSurname.setEditable(false);
			}
			{
				Label lblName = new Label(group, SWT.NONE);
				lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblName.setText("Vorname:");
			}
			{
				_txtGivenname = new Text(group, SWT.BORDER);
				_txtGivenname.setEditable(false);
				_txtGivenname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			}
			{
				Label lblStrae = new Label(group, SWT.NONE);
				lblStrae.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblStrae.setText("Stra\u00DFe:");
			}
			{
				_txtStreet = new Text(group, SWT.BORDER);
				_txtStreet.setEditable(false);
				_txtStreet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			}
			{
				Label lblPlzOrt = new Label(group, SWT.NONE);
				lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblPlzOrt.setText("PLZ:");
			}
			{
				_txtPLZ = new Text(group, SWT.BORDER);
				_txtPLZ.setEditable(false);
				GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
				gd_text_2.widthHint = 206;
				_txtPLZ.setLayoutData(gd_text_2);
				{
					ControlDecoration controlDecoration = new ControlDecoration(_txtPLZ, SWT.LEFT | SWT.TOP);
					controlDecoration.setDescriptionText("Some description");
					Image errorImage = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
							.getImage();
					controlDecoration.setImage(errorImage);
				}
			}
			{
				Label lblPlzOrt = new Label(group, SWT.NONE);
				lblPlzOrt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblPlzOrt.setText("Ort:");
			}
			{
				_txtCity = new Text(group, SWT.BORDER);
				_txtCity.setEditable(false);
				_txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				{
					ControlDecoration controlDecoration = new ControlDecoration(_txtCity, SWT.LEFT | SWT.TOP);
					controlDecoration.setDescriptionText("Some description");
					Image errorImage = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
							.getImage();
					controlDecoration.setImage(errorImage);

					String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum" };

					new AutoCompleteField(_txtCity, new TextContentAdapter(), cities);
				}
			}
			{
				Label lblLand = new Label(group, SWT.NONE);
				lblLand.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblLand.setText("Land:");
			}
			{
				ComboViewer comboViewer = new ComboViewer(group, SWT.NONE);
				comboViewer.setContentProvider(ArrayContentProvider.getInstance());
				// comboViewer.setInput(AddressBookServices.getAddressService().getAllCountries());
				comboViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {

						return "foo";
					}
				});
				Combo combo = comboViewer.getCombo();
				combo.setEnabled(false);
				combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
				combo.select(0);
				{
					ControlDecoration controlDecoration = new ControlDecoration(group, SWT.LEFT | SWT.TOP);
					controlDecoration.setDescriptionText("Some description");
				}
			}
		}
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
	}
	
	@Inject
	void updateEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			_txtGivenname.setText(employee.getGivenname());
			_txtSurname.setText(employee.getSurname());
		}
	}
}
