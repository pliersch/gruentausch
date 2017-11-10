package gruentausch.model;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Activity extends Unmarshaller.Listener {

	private String begin;
	private String end;
	private String task;
	private int kilometers;
	private Day parent;
	private String customerId;

	public Activity() {

	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getKilometers() {
		return kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Day getParent() {
		return parent;
	}

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.parent = (Day) parent;
	}

	public boolean isValid() {
		boolean validBegin = begin != null && begin != "";
		boolean validEnd = end != null && end != "";
		boolean validTask = task != null && task != "";
		boolean validCustomer = customerId != null && customerId != "";
		return validBegin && validEnd && validCustomer && validTask;
	}

}
