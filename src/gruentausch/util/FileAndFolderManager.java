package gruentausch.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;

import gruentausch.parts.LoggingPart;

public class FileAndFolderManager {

	public static File createFolder(String path) throws IOException {
		URL url = Platform.getInstanceLocation().getURL();
		String s = url.getPath() + path;

		// String string = Platform.getLocation().toString();
		File file = new File(s);
		if (!file.exists() && !file.isDirectory()) {
			path = file.getAbsolutePath();
			File f = new File(path);
			f.mkdir();
			return f;
		}

		return file;
	}

	public static File createFile(String path) throws IOException {
		URL url = Platform.getInstanceLocation().getURL();
		File file = null;
		LoggingPart.log(url.getFile() + path);
		file = new File(url.getFile() + path);
		if (!file.exists()) {
			path = file.getAbsolutePath();
			File f = new File(path);
			f.createNewFile();
			return f;
		}
		return file;
	}
}
