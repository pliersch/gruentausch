package gruentausch.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

public class StaffTreeSamplePart {

	private TreeViewer viewer;

	@PostConstruct
	public void postConstruct(Composite parent) {
		viewer = new TreeViewer(parent);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setInput(new String[] { "Simon Scholz", "Lars Vogel", "Dirk Fauth", "Wim Jongman", "Tom Schindl" });

		// Tree tree = (Tree) viewer.getControl();

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	class TreeContentProvider implements ITreeContentProvider {
		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return null;
		}
	}

	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	// tracks the active part
	@Inject
	@Optional
	public void receiveActivePart(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		if (activePart != null) {
			System.out.println("Active part changed " + activePart.getLabel());
		}
	}
}