package gruentausch.util;

import java.util.Calendar;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class RepeatingJob extends Job {
	private boolean running = true;
	private int repeat;

	public RepeatingJob(int repeat) {
		super("Repeating Job");
		this.repeat = repeat;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		schedule(repeat);
		// TODO what about IStatus.WARNING, IStatus.ERROR, IStatus.INFO and
		// IStatus.CANCEL
		checkDate();
		return Status.OK_STATUS;
	}

	private void checkDate() {
		Calendar calendar = Calendar.getInstance();
		createFolder(calendar.get(Calendar.YEAR));
	}

	private void createFolder(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean shouldSchedule() {
		return running;
	}

	public void stop() {
		running = false;
	}
}
