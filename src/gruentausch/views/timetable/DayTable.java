package gruentausch.views.timetable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import gruentausch.model.Activity;
import gruentausch.model.Day;
import gruentausch.views.ViewDataChangeHandler;
import gruentausch.views.timetable.editingsupport.BeginEditingSupport;
import gruentausch.views.timetable.editingsupport.CustomerIdEditingSupport;
import gruentausch.views.timetable.editingsupport.EndEditingSupport;
import gruentausch.views.timetable.editingsupport.KilometerEditingSupport;
import gruentausch.views.timetable.editingsupport.TaskEditingSupport;

public class DayTable {

	// protected TableViewerExtended viewer;
	protected TableViewer viewer;
	private ViewDataChangeHandler _handler;

	boolean addExtraRow = true;
	private List<Activity> activities;

	@PostConstruct
	public void createControls(Composite parent) {
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
		activities = new ArrayList<>();
		Activity activity = new Activity();
		activity.setBegin("08:10");
		activity.setEnd("18:10");
		activity.setKilometers(10);
		activity.setCustomerId("DE77100100100");
		activity.setTask("Wald");
		activities.add(activity);

		viewer.setInput(activities);
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
				ViewerCell source = (ViewerCell) event.getSource();
				Activity activity = (Activity) source.getElement();
				if (activity.isValid()) {
					_handler.handleDataChange(activities);
				}
			}

			@Override
			public void afterEditorActivated(ColumnViewerEditorActivationEvent event) {
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
		String[] titles = { "Beginn", "Ende", "Kunde", "Tätigkeit", "Kilometer" };
		int[] bounds = { 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Activity) element).getBegin();
			}
		});
		col.setEditingSupport(new BeginEditingSupport(viewer));

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Activity) element).getEnd();
			}
		});
		col.setEditingSupport(new EndEditingSupport(viewer));

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Activity) element).getCustomerId().toString();
			}
		});
		col.setEditingSupport(new CustomerIdEditingSupport(viewer));

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Activity) element).getTask();
			}
		});
		col.setEditingSupport(new TaskEditingSupport(viewer));

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String result = null;
				int kilometers = ((Activity) element).getKilometers();
				if (kilometers > 0) {
					result = String.valueOf(kilometers);
				}
				return result;
			}
		});
		col.setEditingSupport(new KilometerEditingSupport(viewer));
	}

	public void updateTable(Day day) {
		activities = day.getActivities();
		if (activities.isEmpty()) {
			addNewEmptyRow();
		}
		viewer.setInput(activities);
	}

	public void addNewEmptyRow() {
		Activity activity = new Activity();
		activity.setBegin("");
		activity.setEnd("");
		activity.setCustomerId("");
		activity.setTask("");
		activities.add(activity);
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

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void setDataViewHandler(ViewDataChangeHandler handler) {
		_handler = handler;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public List<Activity> getActivities() {
		activities.remove(activities.size() - 1);
		return activities;
	}
}
