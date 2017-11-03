package gruentausch.handlers;

import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SwitchPerspectiveHandler {

	@Execute
	public void execute(ParameterizedCommand commandParameters, EPartService partService, MApplication application,
			EModelService modelService) throws CoreException {

		if (null == commandParameters) {
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> parameterMap = commandParameters.getParameterMap();
		String perspecitve = parameterMap.get("gruentausch.commandparameter.perspective");

		MPerspective element = (MPerspective) modelService.find(perspecitve, application);
		partService.switchPerspective(element);

	}
}
