package gruentausch.util;

import java.util.regex.Pattern;

public class RegExUtil {

	public static boolean validateName(String name) {
		return Pattern.matches( "^[a-zA-ZäöüÄÖÜ ,.'-]+$", name ) ;
	}
	
	public static boolean validatePLZ(String plz) {
		return Pattern.matches( "[0-9]{5}", plz ) ;
	}
	
	public static boolean validateHourAndMinutes(String time) {
		return Pattern.matches( "^([0-1]?\\d|2[0-3])(:([0-5]?\\d))?$", time ) ;
	}
	
	// minutes and seconds are optional
//	public static boolean validateTime(String time) {
//		return Pattern.matches( "^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?$", time ) ;
//	}

}
