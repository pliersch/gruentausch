package toolbars;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("restriction")
public class PerspectiveControl {

	private Button btnDefault;
	private Button btnEmployee;
	private Button btnClient;

	@Inject
	ECommandService commandService;

	@Inject
	EHandlerService service;

	@PostConstruct
	public void createGui(final Composite parent) {

		ButtonSelectionListener listener = new ButtonSelectionListener();

		btnDefault = new Button(parent, SWT.NONE);
		btnDefault.setBounds(0, 0, 105, 35);
		btnDefault.setText("Standard");
		btnDefault.addSelectionListener(listener);

		btnEmployee = new Button(parent, SWT.NONE);
		btnEmployee.setBounds(0, 0, 105, 35);
		btnEmployee.setText("Mitarbeiter");
		btnEmployee.addSelectionListener(listener);

		btnClient = new Button(parent, SWT.NONE);
		btnClient.setBounds(0, 0, 105, 35);
		btnClient.setText("Kunden");
		btnClient.addSelectionListener(listener);
	}

	private void executeCommand(String commandId, String perspectiveId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gruentausch.commandparameter.perspective", perspectiveId);
		ParameterizedCommand command = commandService.createCommand(commandId, parameters);
    
    if (service.canExecute(command)) {
      service.executeHandler(command);
    }
	}

	class ButtonSelectionListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			Button source = (Button) e.getSource();
			String commandId;
			String perspective;
			if (source.equals(btnDefault)) {
				commandId = "gruentausch.command.perspective.default";
				perspective = "gruentausch.perspective.default";
			} else if (source.equals(btnEmployee)) {
				commandId = "gruentausch.command.perspective.employee";
				perspective = "gruentausch.perspective.employee";
			} else {
				commandId = "gruentausch.command.perspective.customer";
				perspective = "gruentausch.perspective.customer";
			}
			executeCommand(commandId, perspective);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

	}

}
