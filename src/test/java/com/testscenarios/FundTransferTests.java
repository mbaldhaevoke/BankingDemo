package com.testscenarios;

import com.reusable.FundTransferFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author Evoke Technologies
 * @version V1.0 Each function is annotated with @Test (Run, Run All, Debug)
 */

public class FundTransferTests extends FundTransferFunctions {


	private static final Logger log = LoggerFactory.getLogger(FundTransferTests.class);

	@BeforeClass
	public void startClass() throws Throwable {
		initialize();
		parentStartTest("Fund Transfer Page");
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
		accountSummaryDetails();
		logout();
	}

}