package com.baselibrary;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.options.AriaRole;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class KeyUtilities extends KeySupport {

	/**
	Clicks on an element identified by its text.
	@param locator The locator for the element's text.
	@param ElementName The name of the element.
	*/
	
	public void clickText(String locator, String ElementName) {
		try {
			if(driver.waitForSelector("'"+locator+"'").isEnabled()) {				
				driver.locator("'"+locator+"'").first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			} else if(driver.getByRole(AriaRole.BUTTON).first().isEnabled()) {
				driver.locator("'"+locator+"'").first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			} else {
				LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, null);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, e);
		}
	}

	/**
	Clicks on an element identified by its locator.
	@param locator The locator for the element.
	@param ElementName The name of the element.
	*/
	
	public void clickElement(String locator, String ElementName){
		try {
			if(driver.waitForSelector(locator).isEnabled()) {
				driver.locator(locator).first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, e);
		}
	}

	/**
	Enters a value into an input field.
	@param locator The locator for the input field.
	@param value The value to enter into the input field.
	*/
	
	public void enterValue(String locator, String value){
		try {
			if(driver.waitForSelector(locator).isEnabled()) {
				driver.fill(locator,value);
				LOGPASS(SHOULD_ENTER, SUCCESSFULLY_ENTERED, value);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_ENTER, FAILED_TO_ENTER, value, e);
		}
	}

	/**
	 Verifies that an element is displayed on the page.
	 @param ele The locator for the element.
	 @param elementName The name of the element.
	 @throws Exception If the element is not displayed or if an error occurs while verifying its display.
	 */

	public void verifyElementDisplayed(String ele, String elementName) {
		try {
			driver.waitForSelector("'"+ele+"'").isVisible();
			driver.locator("'"+ ele + "'").first().scrollIntoViewIfNeeded();
			LOGPASS(SHOULD_DISPLAY, SUCCESSFULLY_DISPLAYED, elementName);
		} catch (Exception e) {
			LOGFAIL(SHOULD_DISPLAY, FAILED_TO_DISPLAY, elementName, e);
		}
	}

	public HashMap<String, String> readJsonFile(String jsonFN, String parentNode, String childNode, String testcaseId) throws Throwable {
		HashMap<String, String> map = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			reader = new FileReader(System.getProperty("user.dir") + jsonFN + ".json");
			jsonParser = objectMapper.createParser(reader);
			rootNode = new ObjectMapper().readTree(jsonParser);
			Iterator<Map.Entry<String, JsonNode>> i = rootNode.path(parentNode).get(childNode).get(testcaseId).fields();
			map = new HashMap<>();
			while (i.hasNext()) {
				Map.Entry<String, JsonNode> e = i.next();
				map.put(e.getKey(), e.getValue().textValue());
			}
		} catch (FileNotFoundException e) {
			logger.fail(e.getMessage());
		}
		return map;
	}
}
