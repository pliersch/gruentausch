package gruentausch.parts;

import java.net.URL;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import gruentausch.model.Day;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;

public class TimeTablePart {

	public static final String ID = "de.vogella.jface.tableviewer.view";
	private static final Image CHECKED = createImageDescriptor("icons/checked.gif");
	private static final Image UNCHECKED = createImageDescriptor("icons/unchecked.gif");
	

	private TableViewer viewer;
	private Month month;
	
	
	@Inject
	void updateDescription(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Month month) {
		if (month != null) {
			this.month = month;
			if (viewer != null /* && !viewer.isDisposed() */) {
				viewer.setInput(month.getDays());
				Table table = viewer.getTable();
				TableItem[] items = table.getItems();
				Day day;
				Calendar calendar;
				Color weekendColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
				
				for (TableItem tableItem : items) {
					day = (Day) tableItem.getData();
					calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), day.getDay());
					if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						tableItem.setBackground(weekendColor);
					}
				}
			}
		}
	}
	
//	@Inject
//	void updateDescription(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Employee employee) {
//		if (employee != null) {
//			if (viewer != null /* && !viewer.isDisposed() */) {
//				System.out.println(employee.getGivenname());
//			}
//		}
//	}

	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		createViewer(parent);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());

		// make the selection available to other views
		// getSite().setSelectionProvider(viewer);

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

//	public TableViewer getViewer() {
//		return viewer;
//	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Tag", "Beginn", "Ende", "Urlaub" };
		int[] bounds = { 100, 100, 100, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return Integer.toString(day.getDay());
			}
		});

		// second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return day.getBegin();
			}
		});

		// now the gender
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return day.getEnd();
			}
		});

		// now the status married
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				Day day = (Day) element;
				Calendar calendar = CalendarUtil.getCalendar(month.getYear(), month.getMonth(), day.getDay());
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private static Image createImageDescriptor(String imagePath) {
		Bundle bundle = FrameworkUtil.getBundle(TimeTablePart.class);
		URL url = FileLocator.find(bundle, new Path(imagePath), null);
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
		return imageDescriptor.createImage();
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
