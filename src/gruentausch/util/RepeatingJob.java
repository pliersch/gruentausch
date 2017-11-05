package gruentausch.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class RepeatingJob extends Job {
	private boolean running = true;

	public RepeatingJob() {
		super("Repeating Job");
	}

	protected IStatus run(IProgressMonitor monitor) {
		schedule(10000);
		return Status.OK_STATUS;
	}

	public boolean shouldSchedule() {
		return running;
	}

	public void stop() {
		running = false;
	}
}
