package gruentausch.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Day {
	
	private int day;
	private String begin;
	private String end;
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
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

}
