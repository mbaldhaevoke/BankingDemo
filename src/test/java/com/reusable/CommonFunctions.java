package com.reusable;

import com.baselibrary.KeyUtilities;

import java.util.regex.Pattern;


public class CommonFunctions extends KeyUtilities {

	public void login() throws Throwable {
		childStartTest("Login Test");
		enterValue(BELOW("Email"), "leela@gmail.com");
		enterValue(BELOW("Password"), "Leela@123");
		clickText("Login", "Login");
		verifyElementDisplayed("LogOut", "LogOut");
		closeChildTest();
	}

	public void logout() throws Throwable {
		childStartTest("Logout");
		try {
			if(driver.getByText(Pattern.compile("Logout1", Pattern.CASE_INSENSITIVE)).isEnabled()) {
				driver.getByText(Pattern.compile("Logout", Pattern.CASE_INSENSITIVE)).click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, "Logout");
			} else {
				LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, "Logout", null);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, "Logout", e);
		}
		closeChildTest();
	}




}