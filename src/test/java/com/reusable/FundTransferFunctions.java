package com.reusable;

public class FundTransferFunctions extends CommonFunctions {

    public void accountSummaryDetails() throws Throwable {
        childStartTest("Account Summary Details");
        clickText("Payment method", "Payment method");
        verifyElementDisplayed("Account Holder Name", "Account Holder Name");
        verifyElementDisplayed("Account Type", "Account Type");
        verifyElementDisplayed("Account Number", "Account Number");
        verifyElementDisplayed("IFSC Code", "IFSC Code");
        verifyElementDisplayed("Available Balance in Rupees", "Available Balance in Rupees");
        verifyElementDisplayed("Account Branch", "Account Branch");
        closeChildTest();
    }
}
