package gruentausch.util;

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

	protected IStatus run(IProgressMonitor monitor) {
		schedule(repeat);
		return Status.OK_STATUS;
	}

	public boolean shouldSchedule() {
		return running;
	}

	public void stop() {
		running = false;
	}
}
