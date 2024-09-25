package com.testscenarios;

import com.reusable.BankingFunctions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author Evoke Technologies
 * @version V1.0 Each function is annotated with @Test (Run, Run All, Debug)
 */

public class LoginTests extends BankingFunctions {


	@BeforeClass
	public void startClass() throws Throwable {
		initialize();
		parentStartTest("Login Page");
		invokeBrowser();
	}

	@AfterClass
	public void closeParentReport() throws Exception {
		closeParentReports();
		if (browser != null) {
			browser.close();
		}
	}

	@Test
	public void validLogin() throws Throwable {
		login();
		logout();
	}
}