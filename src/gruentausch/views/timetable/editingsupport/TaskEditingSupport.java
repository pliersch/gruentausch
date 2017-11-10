package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import gruentausch.model.Activity;

public class TaskEditingSupport extends EditingSupport {

  private final TableViewer viewer;
  private final CellEditor editor;

  public TaskEditingSupport(TableViewer viewer) {
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
		return ((Activity) element).getTask();
  }

  @Override
  protected void setValue(Object element, Object userInputValue) {
		((Activity) element).setTask(String.valueOf(userInputValue));
      viewer.update(element, null);
  }
}