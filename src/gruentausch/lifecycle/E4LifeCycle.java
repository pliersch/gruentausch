package gruentausch.lifecycle;

import java.io.IOException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import gruentausch.model.Clients;
import gruentausch.model.Team;
import gruentausch.util.FileAndFolderManager;
import gruentausch.util.Logger;
import gruentausch.util.XMLManager;

/**
 * This is a stub implementation containing e4 LifeCycle annotated
 * methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references
 * this class.
 **/
@SuppressWarnings("restriction")
public class E4LifeCycle {

	@PostContextCreate
	void postContextCreate(IApplicationContext appContext) {
		final Shell shell = new Shell(SWT.SHELL_TRIM);
		// LoginDialog dialog = new LoginDialog(shell);

		// close the static splash screen
//		appContext.applicationRunning();

		// position the shell
		setLocation(shell.getDisplay(), shell);

		// if (dialog.open() != Window.OK) {
		// // close the application
		// System.exit(-1);
		// }

		try {
			FileAndFolderManager.createFolder("data");
			FileAndFolderManager.createFile("data/Mitarbeiter.xml");
			FileAndFolderManager.createFile("data/Kunden.xml");
		} catch (IOException e1) {
			Logger.log(e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void setLocation(Display display, Shell shell) {
		Monitor monitor = display.getPrimaryMonitor();
		Rectangle monitorRect = monitor.getBounds();
		Rectangle shellRect = shell.getBounds();
		int x = monitorRect.x + (monitorRect.width - shellRect.width) / 2;
		int y = monitorRect.y + (monitorRect.height - shellRect.height) / 2;
		shell.setLocation(x, y);
	}

	@PreSave
	void preSave(IEclipseContext workbenchContext) {
		System.out.println("preSave");
	}

	@ProcessAdditions
	void processAdditions(IEclipseContext workbenchContext) {
		Team team = (Team) new XMLManager().readFile("data/Mitarbeiter.xml", Team.class);
		workbenchContext.set(Team.class, team);
		Clients clients = (Clients) new XMLManager().readFile("data/Kunden.xml", Clients.class);
		workbenchContext.set(Clients.class, clients);
		System.out.println("@ProcessAdditions");
	}

	@ProcessRemovals
	void processRemovals(IEclipseContext workbenchContext) {
		System.out.println("@ProcessRemovals");
	}
}
