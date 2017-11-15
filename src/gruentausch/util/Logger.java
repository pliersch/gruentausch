package gruentausch.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Logger {

	private static Logger instance;
	protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private ArrayList<String> messages = new ArrayList<>();

	private Logger() {
	}

	private static Logger getInstance() {
		if (Logger.instance == null) {
			Logger.instance = new Logger();
		}
		return Logger.instance;
	}


	public static void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		getInstance().propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public static void removePropertyChangeListener(PropertyChangeListener listener) {
		getInstance().propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public static void log(String msg) {
		getInstance().messages.add(msg);
		getInstance().propertyChangeSupport.firePropertyChange("logging", null, msg);
		System.out.println(msg);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> getFullLog() {
		return (ArrayList<String>) getInstance().messages.clone();
	}

	public static String getFullLogAsString() {
		ArrayList<String> fullLog = getFullLog();
		String result = "";
		for (String string : fullLog) {
			result += string + "\n";
		}
		return result;
	}

}
