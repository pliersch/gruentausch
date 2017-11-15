package gruentausch.parts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import gruentausch.model.Activity;
import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;
import gruentausch.views.ViewDataChangeHandler;
import gruentausch.views.timetable.DayTable;
import gruentausch.views.timetable.MonthTable;

public class MonthTablePart extends MonthTable implements ViewDataChangeHandler {

	private Employee employee;

	@Inject
	private MPart part;
	private Group groupDetail;

	private DayTable dayTable;

	private Button btnEdit;
	private Button btnSave;
	private Button btnVacation;

	private ViewDataChangeHandler _handler;

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
		// data1.top = new FormAttachment(table, 5);
		data1.bottom = new FormAttachment(100, -10);

		btnVacation = new Button(groupDetail, SWT.CHECK);
		btnVacation.setLayoutData(data1);
		btnVacation.setEnabled(false);
		btnVacation.setText("Urlaub");
		btnVacation.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.setEnabled(!btnVacation.getSelection());
				btnSave.setEnabled(btnVacation.getSelection());
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
		btnEdit.setText("Bearbeiten");
		btnEdit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = btnVacation.getEnabled();
				btnVacation.setEnabled(!enabled);
				table.setEnabled(!enabled);
				if (enabled) {
					btnVacation.setSelection(false);
					btnSave.setEnabled(false);
					btnEdit.setText("Bearbeiten");
				} else {
					btnEdit.setText("Abbrechen");
				}
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
		btnSave.setText("Speichern");
		btnSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setEnabled(false);
				dayTable.addNewEmptyRow();
				btnVacation.setSelection(false);
				btnVacation.setEnabled(false);
				table.setEnabled(false);
				btnEdit.setText("Bearbeiten");
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
					updateDetail((Day) firstElement);
				} else {
					updateDetail(null);
				}
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
						} else {
							btnEdit.setEnabled(false);
						}
					}
				});
			}
		});
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

			btnEdit.setEnabled(false);
			btnEdit.setText("Bearbeiten");
			btnVacation.setEnabled(false);

			if (viewer != null /* && !viewer.isDisposed() */) {
				List<Day> days = addEmptyDays(month);
				viewer.setInput(days);

				Table table = viewer.getTable();
				TableItem[] items = table.getItems();
				Day day;
				Calendar calendar;
				Color weekendColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
				Color missingColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);

				for (TableItem tableItem : items) {
					day = (Day) tableItem.getData();
					calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), day.getDay());
					if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
							|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						tableItem.setBackground(weekendColor);
					} else if (!day.isVacation() && day.getBegin() == null) {
						tableItem.setBackground(missingColor);
					}
				}
			}
		}
	}

	private List<Day> addEmptyDays(Month month) {
		Calendar calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Day> employeeDays = month.getDays();
		List<Day> days = new ArrayList<>(daysInMonth);

		int position = 0;

		for (int i = 0; i < daysInMonth; i++) {
			Day day = employeeDays.get(position);
			if (day.getDay() == i + 1) {
				position++;
				days.add(day);
			} else {
				Day newDay = new Day();
				newDay.setDay(i + 1);
				newDay.setCalendar(calendar);
				days.add(newDay);
			}

		}
		return days;
	}

	@Override
	public void handleDataChange(Object object) {
		Activity activity = (Activity) object;
		if (activity.isValid()) {
			btnSave.setEnabled(true);
		} else {
			btnSave.setEnabled(false);
		}
	}
}
