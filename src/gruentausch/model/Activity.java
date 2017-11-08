package gruentausch.model;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Activity extends Unmarshaller.Listener {

	private String begin;
	private String end;
	private Day parent;

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

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.parent = (Day) parent;
	}

	public Day getParent() {
		return parent;
	}

}
