package gruentausch.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchHandler extends Thread {
	WatchService watchService;
	Path dirToWatch;
	private Listener listener;

	public interface Listener {
		void handleFilesChanged();
	}

	public WatchHandler(WatchService watchService, Path dirToWatch, Listener listener) {
		this.watchService = watchService;
		this.dirToWatch = dirToWatch;
		this.listener = listener;
	}

	@Override
	public void run() {
		System.out.println("run");
		for (;;) {
			try {
				WatchKey key = watchService.take(); // blockiert
				List<WatchEvent<?>> eventList = key.pollEvents();
				for (WatchEvent<?> e : eventList) {
					listener.handleFilesChanged();
					System.out.print(e.kind() + " -> ");
					Path name = (Path) e.context();
					// System.out.print(name.getParent());
					// context liefert nur den Dateinamen, parent ist null !
					Path path = dirToWatch.resolve(name);
					System.out.print(path);
					if (Files.isDirectory(path))
						System.out.println(" <dir>");
					else
						System.out.println(" <file<");
				}
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			} catch (InterruptedException ex) {
				Logger.log(ex.getMessage());
			}
		}
	}
}
