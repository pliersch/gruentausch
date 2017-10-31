package gruentausch.views.timetable.editingsupport;

import org.eclipse.jface.viewers.TableViewer;

import gruentausch.model.Day;

public class BeginEditingSupport extends TimeEditingSupport {

	public BeginEditingSupport(TableViewer viewer) {
		super(viewer);
		
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