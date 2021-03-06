package gruentausch.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.model.Person;
import gruentausch.model.Team;
import gruentausch.model.Year;
import gruentausch.util.CalendarUtil;

public class StaffTreePart {

	@Inject
	EPartService partService;

	@Inject
	private ESelectionService selectionService;

	private TreeViewer viewer;

	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService, MApplication application) {

		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		menuService.registerContextMenu(viewer.getControl(), "gruentausch.popupmenu.edit.staff");

		TreeViewerColumn mainColumn = new TreeViewerColumn(viewer, SWT.NONE);
		mainColumn.getColumn().setText("Name");
		mainColumn.getColumn().setWidth(300);
		mainColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));

		IEclipseContext context = application.getContext();
		Team team = context.get(Team.class);
		team.addPropertyChangeListener("employees", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object newValue = evt.getNewValue();
				viewer.setInput(newValue);
			}
		});
		if (team != null) {
			viewer.setInput(team);
		}

		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof Month) {
					displayPart("gruentausch.part.table.month");
				}
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object firstElement = selection.getFirstElement();

				if (firstElement instanceof Employee) {
					Person employee = (Person) firstElement;
					selectionService.setSelection(employee);
				} else if (firstElement instanceof Year) {
					Year year = (Year) firstElement;
					selectionService.setSelection(year.getParent());
					selectionService.setSelection(year);
				} else if (firstElement instanceof Month) {
					Month month = (Month) firstElement;
					selectionService.setSelection(month.getParent().getParent());
					selectionService.setSelection(month.getParent());
					selectionService.setSelection(month);
				}
			}
		});

		Tree tree = (Tree) viewer.getControl();
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Object data = item.getData();
				// if (data instanceof Employee) {
				// displayPart("gruentausch.part.filebrowser");
				// } else if (data instanceof Year) {
				// displayPart("gruentausch.part.mitarbeiter");
				// }
				TreeItem item = (TreeItem) e.item;
				if (item.getItemCount() > 0) {
					item.setExpanded(!item.getExpanded());
					viewer.refresh();
				}
			}
		});
	}

	private void displayPart(String id) {
		MPart mpart = partService.findPart(id);
		mpart.setVisible(true);
		partService.showPart(mpart, PartState.CREATE);
		partService.bringToTop(mpart);
	}

	// private void hidePart(){
	// MPart mpart=partService.findPart("gruentausch.part.filebrowser");
	// mpart.setVisible(false);
	// partService.hidePart(mpart);
	// }

	private ImageDescriptor createImageDescriptor(String filename) {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + filename + ".png"), null);
		return ImageDescriptor.createFromURL(url);
	}

	class ViewContentProvider implements ITreeContentProvider {

		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			Team t = (Team) inputElement;
			return t.getEmployees().toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Employee) {
				Employee employee = (Employee) parentElement;
				return employee.getYears().toArray();
			} else if (parentElement instanceof Year) {
				Year year = (Year) parentElement;
				return year.getMonths().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			Object parent = null;
			if (element instanceof Year) {
				parent = ((Year) element).getParent();
			} else if (element instanceof Month) {
				parent = ((Month) element).getParent();
			}
			return parent;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Team) {
				return true;
			} else if (element instanceof Employee) {
				return true;
			} else if (element instanceof Year) {
				return true;
			} else if (element instanceof Month) {
				return false;
			}
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private ResourceManager resourceManager;

		@Override
		public StyledString getStyledText(Object element) {
			if (element instanceof Employee) {
				Person employee = (Person) element;
				StyledString styledString = new StyledString(employee.getGivenname());
				return styledString;
			}
			if (element instanceof Year) {
				Year year = (Year) element;
				StyledString styledString = new StyledString(Integer.toString(year.getYear()));
				return styledString;
			}
			if (element instanceof Month) {
				Month month = (Month) element;
				StyledString styledString = new StyledString(CalendarUtil.getMonth(month.getMonth()));
				return styledString;
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof Employee) {
				return getResourceManager().createImage(createImageDescriptor("avatar"));
			}

			return super.getImage(element);
		}

		@Override
		public void dispose() {
			// garbage collection system resources
			if (resourceManager != null) {
				resourceManager.dispose();
				resourceManager = null;
			}
		}

		protected ResourceManager getResourceManager() {
			if (resourceManager == null) {
				resourceManager = new LocalResourceManager(JFaceResources.getResources());
			}
			return resourceManager;
		}
	}

	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}