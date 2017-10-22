package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class TabPart {
	
	 @PostConstruct
   public void createControls(Composite parent) {
		 TabFolder folder = new TabFolder(parent, SWT.NONE);
		 
     // Tab 1
     TabItem tab1 = new TabItem(folder, SWT.NONE);
     tab1.setText("Tab 1");

     // Create the HORIZONTAL SashForm.
     SashForm sashForm = new SashForm(folder, SWT.HORIZONTAL);
     
     new Button(sashForm, SWT.PUSH).setText("Left");
     new Button(sashForm, SWT.PUSH).setText("Right");
     sashForm.setWeights(new int[]{1,2});

     tab1.setControl(sashForm);

     // Tab 2
     TabItem tab2 = new TabItem(folder, SWT.NONE);
     tab2.setText("Tab 2");

     Group group = new Group(folder, SWT.NONE);
     group.setText("Group in Tab 2");

     Button button = new Button(group, SWT.NONE);
     button.setText("Button in Tab 2");
     button.setBounds(10, 50, 130, 30);

     Text text = new Text(group, SWT.BORDER);
     text.setText("Text in Tab 2");
     text.setBounds(10, 90, 200, 20);

     tab2.setControl(group);
	 }
}
