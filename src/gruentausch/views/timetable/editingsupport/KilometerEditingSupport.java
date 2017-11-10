package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import gruentausch.model.Activity;
import gruentausch.util.RegExUtil;

public class KilometerEditingSupport extends EditingSupport {

  private final TableViewer viewer;
  private final CellEditor editor;

  public KilometerEditingSupport(TableViewer viewer) {
      super(viewer);
      this.viewer = viewer;
      this.editor = new TextCellEditor(viewer.getTable());
		editor.setValidator(new ICellEditorValidator() {

			@Override
			public String isValid(Object value) {
				if (value instanceof String) {
					if (RegExUtil.validateKilometer((String) value)) {
						System.out.println("valid");
						return null;
					}
				}
				System.out.println("not valid");
				return "Eingabe muss eine Ganzzahl sein";
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

  @Override
  protected Object getValue(Object element) {
		String result = "";
		int kilometers = ((Activity) element).getKilometers();
		if (kilometers > 0) {
			result = String.valueOf(kilometers);
		}
		return result;
  }

  @Override
  protected void setValue(Object element, Object userInputValue) {
		if (userInputValue != "") {
			Integer km = Integer.valueOf((String) userInputValue);
			((Activity) element).setKilometers(km);
			viewer.update(element, null);
		}
  }
}