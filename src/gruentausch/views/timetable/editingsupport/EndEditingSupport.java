package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.viewers.TableViewer;

import gruentausch.model.Day;

public class EndEditingSupport extends TimeEditingSupport {

	public EndEditingSupport(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getValue(Object element) {
		if (element != null) {
			String end = ((Day) element).getEnd();
			if (end != null) {
				return end;
			}
		}
		return "";
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		((Day) element).setEnd(String.valueOf(userInputValue));
		viewer.update(element, null);
	}
}