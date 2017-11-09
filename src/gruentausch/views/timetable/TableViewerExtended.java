package gruentausch.views.timetable;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

public class TableViewerExtended extends TableViewer {

	boolean addExtraRow = true;

	public TableViewerExtended(Composite parent) {
		super(parent);
	}

	public TableViewerExtended(Composite composite, int style) {
		super(composite, style);
	}

	@Override
	public void refresh(Object element) {
		if (!addExtraRow) {
			super.refresh(element);
		} else {
			removeEmptyRow();
			super.refresh(element);
		}
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (!addExtraRow) {
			super.inputChanged(input, oldInput);
		} else {
			removeEmptyRow();
			super.inputChanged(input, oldInput);
			@SuppressWarnings("unused")
			TableItem tableItem = new TableItem(getTable(), SWT.NO_BACKGROUND | SWT.NO_FOCUS);
		}

	}

	public void removeEmptyRow() {
		try {
			for (TableItem tableItem : getTable().getItems()) {
				if (tableItem == null || tableItem.getText() == null || "".equals(tableItem.getText())) {
					tableItem.dispose();
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void refresh() {
		if (!addExtraRow) {
			super.refresh();
		} else {
			removeEmptyRow();
			super.refresh();
			@SuppressWarnings("unused")
			TableItem tableItem = new TableItem(getTable(), SWT.NO_BACKGROUND | SWT.NO_FOCUS);
		}
	}

}