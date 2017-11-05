package gruentausch.util;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Logger {

	private static List<String> messages = new ArrayList<>();
	protected static final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(messages);

	public static void log(String msg) {
		messages.add(msg);
	}

}
