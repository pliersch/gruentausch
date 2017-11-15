package gruentausch.handlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import gruentausch.util.Logger;
import gruentausch.util.RepeatingJob;

public class DailyJobHandler {

	@Execute
	public void execute(Shell shell) {
		// TODO current execute job every 60 seconds
		RepeatingJob job = new RepeatingJob(60 * 1000);
		job.addJobChangeListener(new IJobChangeListener() {

			@Override
			public void sleeping(IJobChangeEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void scheduled(IJobChangeEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void running(IJobChangeEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void done(IJobChangeEvent event) {
				int code = event.getResult().getCode();
				String msg = "DailyJobHandler says: ";
				switch (code) {
				case IStatus.OK:
					msg += "OK";
					break;
				case IStatus.WARNING:
					msg += "Warning";
					break;
				case IStatus.ERROR:
					msg += "Error";
					break;
				case IStatus.INFO:
					msg += "Info";
					break;
				case IStatus.CANCEL:
					msg += "Cancel";
					break;

				default:
					msg += "unknown " + code;
					break;
				}
				Logger.log(msg);
			}

			@Override
			public void awake(IJobChangeEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void aboutToRun(IJobChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});
		job.schedule(1000); // start after 1 seconds
	}

}
