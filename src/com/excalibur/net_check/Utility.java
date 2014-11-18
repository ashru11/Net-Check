package com.excalibur.net_check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * check if the mail is valid
	 * 
	 * @param mail
	 *            email to check
	 * @return true if email is valid, false otherwise
	 */
	public static boolean isValidMail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * check password validity
	 * 
	 * @param pass
	 *            password to test
	 * @return true if password is safe enough, false otherwise
	 */
	public static boolean isSafePass(String pass) {
		if (pass.length() < 6) {
			return false;
		}
		int dc = 0, lc = 0;
		for (int i = 0; i < pass.length(); i++) {
			if (Character.isDigit(pass.charAt(i))) {
				dc++;
			} else if (Character.isLetter(pass.charAt(i))) {
				lc++;
			}
		}
		if (dc == 0 || lc == 0) {
			return false;
		}
		return true;
	}

}
