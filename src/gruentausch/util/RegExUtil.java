package gruentausch.util;

import java.util.regex.Pattern;

public class RegExUtil {

	public static boolean validateName(String name) {
		return Pattern.matches( "^[a-zA-ZäöüÄÖÜ ,.'-]+$", name ) ;
	}
	
	public static boolean validatePLZ(String plz) {
		return Pattern.matches( "[0-9]{5}", plz ) ;
	}
}
