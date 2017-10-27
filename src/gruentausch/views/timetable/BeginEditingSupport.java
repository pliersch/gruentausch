package gruentausch.views.timetable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import gruentausch.model.Day;

public class BeginEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;

	public BeginEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
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
  	if (element != null) {
  		String begin = ((Day) element).getBegin();
  		if (begin != null) {
  			return begin;
			}
		}
  	return "";
  }

	@Override
	protected void setValue(Object element, Object userInputValue) {
		((Day) element).setBegin(String.valueOf(userInputValue));
		viewer.update(element, null);
	}
}