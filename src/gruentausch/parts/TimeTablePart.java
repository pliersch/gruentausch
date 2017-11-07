package gruentausch.parts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;
import gruentausch.views.timetable.TimeTable;

public class TimeTablePart extends TimeTable {

	private Text text;
	private Employee employee;

	@Inject
	private MPart part;
	private Group groupDetail;

	@PostConstruct
	public void createControls(Composite parent) {

		Composite container = new Composite(parent, SWT.FILL);
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 20;
		fillLayout.marginWidth = 20;
		container.setLayout(fillLayout);
		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setLocation(0, 0);

		super.createControls(sashForm);

		groupDetail = new Group(sashForm, SWT.SHADOW_IN);
		groupDetail.setText("Detail");
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginBottom = 10;
		gridLayout.marginTop = 10;
		gridLayout.marginLeft = 80;
		gridLayout.marginRight = 10;
		groupDetail.setLayout(gridLayout);

		text = new Text(groupDetail, SWT.BORDER);

		Button btnCheckButton = new Button(groupDetail, SWT.CHECK);
		btnCheckButton.setText("Check Button");

		sashForm.setWeights(new int[] { 2, 1 });

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
					public void run() {
						if (day != null) {
							text.setText(day.getBegin());
						} else {
							text.setText("");
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
}
