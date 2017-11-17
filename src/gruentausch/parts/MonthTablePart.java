package gruentausch.parts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.persistence.Persister;
import gruentausch.util.CalendarUtil;
import gruentausch.views.ViewDataChangeHandler;
import gruentausch.views.timetable.DayTable;
import gruentausch.views.timetable.MonthTable;

public class MonthTablePart extends MonthTable implements ViewDataChangeHandler {

	private final String txtEdit = "Bearbeiten";
	private final String txtCancel = "Abbrechen";
	private final String txtSave = "Speichern";

	@Inject
	private MPart part;
	private Group groupDetail;

	private DayTable dayTable;

	private Button btnEdit;
	private Button btnSave;
	private Button btnVacation;

	private Employee employee;
	private Day day;

	@Override
	@PostConstruct
	public void createControls(Composite parent) {

		Table table;

		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 20;
		fillLayout.marginWidth = 20;
		container.setLayout(fillLayout);
		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setLocation(0, 0);

		super.createControls(sashForm);

		new Label(sashForm, SWT.NONE);
		groupDetail = new Group(sashForm, SWT.NONE);
		groupDetail.setText("Detail");

		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 5;
		formLayout.marginHeight = 5;

		groupDetail.setLayout(formLayout);

		FormData data2 = new FormData();
		data2.left = new FormAttachment(0);
		data2.right = new FormAttachment(100);
		data2.top = new FormAttachment(0);
		data2.bottom = new FormAttachment(90);

		dayTable = new DayTable();
		dayTable.createControls(groupDetail);
		dayTable.setDataViewHandler(this);
		table = dayTable.getViewer().getTable();
		table.setLayoutData(data2);
		table.setEnabled(false);

		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 5);
		data1.bottom = new FormAttachment(100, -10);

		btnVacation = new Button(groupDetail, SWT.CHECK);
		btnVacation.setLayoutData(data1);
		btnVacation.setEnabled(false);
		btnVacation.setText("Urlaub");
		btnVacation.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isVaction = btnVacation.getSelection();
				table.setEnabled(!isVaction);
				dayTable.cleanUp();
				btnSave.setEnabled(isVaction);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		FormData data3 = new FormData();
		data3.top = new FormAttachment(table, 5);
		data3.right = new FormAttachment(100, -10);

		btnEdit = new Button(groupDetail, SWT.NONE);
		btnEdit.setEnabled(false);
		btnEdit.setLayoutData(data3);
		btnEdit.setText(txtEdit);
		btnEdit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isEditMode = btnEdit.getText().equals(txtEdit);
				if (isEditMode) {
					enableDetail();
				} else {
					btnVacation.setSelection(day.isVacation());
					disableDetail();
				}
				// dayTable.cleanUp();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		FormData data4 = new FormData();
		data4.top = new FormAttachment(table, 5);
		data4.right = new FormAttachment(btnEdit, -10);

		btnSave = new Button(groupDetail, SWT.NONE);
		btnSave.setEnabled(false);
		btnSave.setLayoutData(data4);
		btnSave.setText(txtSave);
		btnSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				disableDetail();
				boolean isVacation = btnVacation.getSelection();
				dayTable.addNewEmptyRow();
				// btnVacation.setEnabled(false);
				btnEdit.setText(txtEdit);

				if (isVacation) {
					day.setVacation(isVacation);
					day.setActivities(null);
				} else {
					day.setActivities(dayTable.getActivities());
				}
				Persister.getInstance().update(employee);

				Month month = day.getParent();
				List<Day> days = month.getDays();
				viewer.setInput(days);
				highlightRows(month);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		sashForm.setWeights(new int[] { 20, 1, 10 });

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof Day) {
					day = (Day) firstElement;
					updateDetail(day);
				} else {
					updateDetail(null);
				}
			}

		});
	}

	private void updateDetail(Day day) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				groupDetail.setText("Detail");
				if (day != null) {
					Calendar calendar = day.getCalendar();
					String format = new SimpleDateFormat("EEEE', 'dd. MMMM yyyy", Locale.GERMAN).format(calendar.getTime());
					groupDetail.setText(format);
					btnEdit.setEnabled(true);
					dayTable.updateTable(day);
					if (day.isVacation()) {
						btnVacation.setSelection(true);
					}
				} else {
					btnEdit.setEnabled(false);
				}
			}
		});
	}

	private void disableDetail() {
		btnEdit.setEnabled(true);
		btnSave.setEnabled(false);
		btnEdit.setText(txtEdit);
		btnVacation.setEnabled(false);
		dayTable.getViewer().getTable().setEnabled(false);
	}

	private void enableDetail() {
		btnEdit.setEnabled(true);
		btnEdit.setText(txtCancel);
		btnVacation.setEnabled(true);
		boolean isVacation = btnVacation.getSelection();
		dayTable.getViewer().getTable().setEnabled(!isVacation);
	}

	@Inject
	protected void setEmployee(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
		if (employee != null) {
			this.employee = employee;
		}
	}

	@Inject
	void updateMonth(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Month month) {
		if (month != null) {

			part.setLabel(employee.getGivenname() + " " + CalendarUtil.getMonth(month.getMonth()) + " " + month.getYear());
			disableDetail();
			if (viewer != null /* && !viewer.isDisposed() */) {
				// List<Day> days = addEmptyDays(month);
				List<Day> days = month.getDays();

				viewer.setInput(days);
				highlightRows(month);

			}
		}
	}

	private void highlightRows(Month month) {
		Table table = viewer.getTable();
		TableItem[] items = table.getItems();
		Day day;
		Calendar workingCalendar;
		Calendar today = Calendar.getInstance();
		Color weekendColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		Color missingColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		Color vacationColor = Display.getDefault().getSystemColor(SWT.COLOR_CYAN);

		for (TableItem tableItem : items) {
			day = (Day) tableItem.getData();
			workingCalendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), day.getDay());
			if (workingCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| workingCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				tableItem.setBackground(weekendColor);
			} else if (day.isVacation()) {
				tableItem.setBackground(vacationColor);
			} else if (day.getBegin() == null && workingCalendar.before(today)) {
				tableItem.setBackground(missingColor);
			}
		}
	}

	// private List<Day> addEmptyDays(Month month) {
	// Calendar calendar = CalendarUtil.getCalendar(month.getYear(),
	// month.getMonth(), 1);
	// int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	// List<Day> employeeDays = month.getDays();
	// List<Day> days = new ArrayList<>(daysInMonth);
	//
	// int position = 0;
	//
	// for (int i = 0; i < daysInMonth; i++) {
	// Day day = employeeDays.get(position);
	// if (day.getDay() == i + 1) {
	// position++;
	// days.add(day);
	// } else {
	// Day newDay = new Day();
	// newDay.setDay(i + 1);
	// newDay.setCalendar(calendar);
	// days.add(newDay);
	// }
	// }
	// return days;
	// }

	@Override
	public void handleDataChange(Object object) {
		btnSave.setEnabled(true);
	}
}
