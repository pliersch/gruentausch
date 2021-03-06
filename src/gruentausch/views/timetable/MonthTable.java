package gruentausch.views.timetable;

import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellNavigationStrategy;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import gruentausch.model.Day;
import gruentausch.util.CalendarUtil;
import gruentausch.views.ViewDataChangeHandler;

public class MonthTable {

	private static final Image CHECKED = createImageDescriptor("icons/checked.gif");
	private static final Image UNCHECKED = createImageDescriptor("icons/unchecked.gif");

	protected TableViewer viewer;
	private ViewDataChangeHandler _handler;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout(SWT.NONE));
		createViewer(parent);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		addEditorSupport(viewer);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
	}

	private void addEditorSupport(TableViewer tv) {
		final CellNavigationStrategy cellNavigation = createCellNavigationStrategy(tv);
		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tv,
				new FocusCellOwnerDrawHighlighter(tv), cellNavigation);
		final ColumnViewerEditorActivationStrategy activationStrategy = createEditorActivationStrategy(tv);
		TableViewerEditor.create(tv, focusCellManager, activationStrategy,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		tv.getColumnViewerEditor().addEditorActivationListener(createEditorActivationListener(tv));
	}

	private CellNavigationStrategy createCellNavigationStrategy(TableViewer tv) {
		final Table t = tv.getTable();
		return new CellNavigationStrategy() {
			@Override
			public ViewerCell findSelectedCell(ColumnViewer viewer, ViewerCell currentSelectedCell, Event event) {
				final ViewerCell cell = super.findSelectedCell(viewer, currentSelectedCell, event);
				if (cell != null) {
					t.showColumn(t.getColumn(cell.getColumnIndex()));
				}
				return cell;
			}
		};
	}

	private ColumnViewerEditorActivationListener createEditorActivationListener(TableViewer tv) {
		final Table t = tv.getTable();
		return new ColumnViewerEditorActivationListener() {

			@Override
			public void beforeEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
			}

			@Override
			public void beforeEditorActivated(ColumnViewerEditorActivationEvent event) {
				ViewerCell cell = (ViewerCell) event.getSource();
				t.showColumn(t.getColumn(cell.getColumnIndex()));
			}

			@Override
			public void afterEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
				System.out.println("afterEditorDeactivated");
			}

			@Override
			public void afterEditorActivated(ColumnViewerEditorActivationEvent event) {
				System.out.println("afterEditorActivated");
			}
		};
	}

	private ColumnViewerEditorActivationStrategy createEditorActivationStrategy(TableViewer tv) {
		return new ColumnViewerEditorActivationStrategy(tv) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR);
			}
		};
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Tag", "Beginn", "Ende", "Urlaub" };
		int[] bounds = { 130, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return CalendarUtil.toGermanString(day.getCalendar());
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return day.getBegin();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Day day = (Day) element;
				return day.getEnd();
			}
		});

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
		Bundle bundle = FrameworkUtil.getBundle(MonthTable.class);
		URL url = FileLocator.find(bundle, new Path(imagePath), null);
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
		return imageDescriptor.createImage();
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void setDataViewHandler(ViewDataChangeHandler handler) {
		_handler = handler;
	}
}
