package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import gruentausch.model.Activity;

public class VacationEditingSupport extends EditingSupport {

	private final TableViewer viewer;

	public VacationEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);

	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		Activity activity = (Activity) element;
		return activity.getParent().isVacation();

	}

	@Override
	protected void setValue(Object element, Object value) {
		Activity activity = (Activity) element;
		activity.getParent().setVacation((Boolean) value);
		viewer.update(element, null);
	}
}
