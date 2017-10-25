package gruentausch.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import gruentausch.model.Day;
import gruentausch.model.Employee;
import gruentausch.model.Month;
import gruentausch.model.Team;
import gruentausch.model.Year;

public class DummyCreator {
	
	public Team createDummyContent() {

		Team team = new Team();
		String[] surnames = new String[] { "Panzer", "Doe", "Mustermann" };
		String[] givennames = new String[] { "Paul", "John", "Max" };
		Integer[] years = new Integer[] { 2016, 2017 };

		// months, days and times will added in "for i"

		for (int i = 0; i < surnames.length; i++) {
			Employee employee = new Employee();
			employee.setGivenname(givennames[i]);
			employee.setSurname(surnames[i]);
			team.addEmployee(employee);
			for (int j = 0; j < years.length; j++) {
				List<Year> ys = new ArrayList<Year>();
				Year year = new Year();
				year.setYear(years[j]);
				ys.add(year);
				employee.addYear(year);
				for (int k = 0; k < 12; k++) {
					Month month = new Month();
					month.setMonth(k);
					month.setYear(year.getYear());
					Calendar calendar = CalendarUtil.getCalendar(years[j], k);
					int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					year.addMonth(month);
					List<Day> days = new ArrayList<Day>();
					for (int l = 0; l < daysInMonth; l++) {
						Day day = new Day();
						day.setDay(l+1);
						day.setBegin(getRandomBegin());
						day.setEnd(getRandomEnd());
						days.add(day);
						month.setDays(days);
						
					}
				}
			}
		}
		
//		new XMLManager().writeConsole(team, Team.class);
		
//		new XMLManager().writeConsole(johnPeel, Player.class);
//		new XMLManager().writeConsole(room, Room.class);
//		new XMLManager().writeFile(room, "\\data\\test.xml");
//		new XMLManager().readFile("\\data\\test.xml", Room.class);
		
		return team;
	}
	
	private String getRandomBegin() {
		int hour = ThreadLocalRandom.current().nextInt(6, 9 + 1);
		int minute = ThreadLocalRandom.current().nextInt(10, 58 + 1);
		return hour + ":" + minute;
	}
	
	private String getRandomEnd() {
		int hour = ThreadLocalRandom.current().nextInt(14, 18 + 1);
		int minute = ThreadLocalRandom.current().nextInt(10, 58 + 1);
		return hour + ":" + minute;
	}
}
