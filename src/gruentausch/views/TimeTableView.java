package gruentausch.views;

import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import gruentausch.model.Day;
import gruentausch.util.CalendarUtil;

public class TimeTableView {

	private static final Image CHECKED = createImageDescriptor("icons/checked.gif");
	private static final Image UNCHECKED = createImageDescriptor("icons/unchecked.gif");

	protected TableViewer viewer;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout(SWT.NONE));
		createViewer(parent);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());

		// GridData gridData = new GridData();
		// gridData.verticalAlignment = GridData.FILL;
		// gridData.horizontalSpan = 2;
		// gridData.grabExcessHorizontalSpace = true;
		// gridData.grabExcessVerticalSpace = true;
		// gridData.horizontalAlignment = GridData.FILL;
		// viewer.getControl().setLayoutData(gridData);
	}

	// public TableViewer getViewer() {
	// return viewer;
	// }

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
				return CalendarUtil.toGermanString(day.getCalendar());
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
				if (day.isVacation()) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}
		});
	}

	public void updateTable(List<Day> days) {
		viewer.setInput(days);
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
		Bundle bundle = FrameworkUtil.getBundle(TimeTableView.class);
		URL url = FileLocator.find(bundle, new Path(imagePath), null);
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
		return imageDescriptor.createImage();
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
