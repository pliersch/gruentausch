package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class NavigationPart {

	@PostConstruct
	public void createControls(Composite parent) {

		Tree tree = new Tree(parent, SWT.BORDER);
		
		TreeItem trtmNewTreeitem = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem.setText("New TreeItem");
		
		Tree tree_1 = new Tree(parent, SWT.BORDER);
		tree_1.setBounds(0, 0, 224, 298);

//		for (int i = 0; i < 12; i++) {
//			TreeItem item = new TreeItem(tree, SWT.NONE);
//			item.setText("Item " + i);
//		}
//		TreeItem item = new TreeItem(tree, SWT.NONE, 1);
//		TreeItem[] items = tree.getItems();
//		int index = 0;
//		while (index < items.length && items[index] != item)
//			index++;
//		item.setText("*** New Item " + index + " ***");

	}
}
