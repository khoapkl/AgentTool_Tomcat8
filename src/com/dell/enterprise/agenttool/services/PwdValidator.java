package com.dell.enterprise.agenttool.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PwdValidator {
	private Pattern pattern;
	private Matcher matcher;
	
	public PwdValidator(String PASSWORD_PATTERN) {
		// TODO Auto-generated constructor stub
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public boolean validate(final String password) { 
		matcher = pattern.matcher(password);
		return matcher.matches();
 
	}

}
