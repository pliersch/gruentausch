package gruentausch.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.widgets.Shell;

import gruentausch.preferences.PreferencesDialog;

public class PreferencesHandler {
	@Execute
	public void execute(final Shell shell, MWindow window) {
//		MessageDialog messageDialog = new MessageDialog(shell, "Pref", null, "msg", MessageDialog.INFORMATION, new String[] { "OK", "Cancel"}, 0);
//		messageDialog.open();
		
		PreferenceManager manager = new PreferenceManager();
		PreferencesDialog dialog = new PreferencesDialog(shell, manager);
		dialog.open();
	}
}
