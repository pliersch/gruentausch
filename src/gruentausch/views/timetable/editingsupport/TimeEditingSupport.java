package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import gruentausch.util.RegExUtil;

public abstract class TimeEditingSupport extends EditingSupport {

	protected final TableViewer viewer;
	protected final CellEditor editor;

	public TimeEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		editor.setValidator(new ICellEditorValidator() {

			@Override
			public String isValid(Object value) {
				if (value instanceof String) {
					if (RegExUtil.validateHourAndMinutes((String) value)) {
						System.out.println("valid");
						return null;
					}
				}
				System.out.println("not valid");
				return "Eingabe muss vom Format 'h:mm' oder 'hh:mm' ";
			}
		});

		editor.addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState, boolean newValidState) {
			}

			@Override
			public void cancelEditor() {
			}

			@Override
			public void applyEditorValue() {
				Object value = editor.getValue();
				String msg = editor.getValidator().isValid(value);
				if (msg != null) {
					MessageDialog.openError(viewer.getControl().getShell(), "No did not work", msg);
				}
			}
		});
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}
	
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}