package com.baselibrary;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Configuration class for setting up and managing test configurations.
 */

public class Configurations {

    public Playwright playWright;
    public static Page driver;
    public BrowserContext browserContext;
    public Browser browser;
    public static ExtentReports reports;
    public ExtentTest logger;
    public ExtentTest parentTest;
    public static String reportLocation = "./Reports/"; //make it as environment variable and call from there. on my local machine as well. it used to be ./Reports/
    public Properties prop;
    public static DateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HHmmssSSS");
    public static Date date = new Date();
    public static ExtentSparkReporter spark;
    public JsonParser jsonParser;
    public FileReader reader;
    public JsonNode rootNode;

    /**
     * Initializes the configuration by loading properties from the config file.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void initialize() throws IOException {
        prop = new Properties();
        try (InputStream enviConfig = new FileInputStream(".//config.properties")) {
            prop.load(enviConfig);
        } catch (FileNotFoundException e) {
            logger.log(Status.FAIL, e.getMessage());
        }
    }

    public void initialiseReports() {
        reportLocation = System.getProperty("user.dir") + "\\Reports\\" + "Banking" + "_" + dateformat.format(date) + "\\";
        reports = new ExtentReports();
        reports.setAnalysisStrategy(AnalysisStrategy.CLASS);
        spark = new ExtentSparkReporter(reportLocation + "BankingTestReport"+ ".html");
        reports.attachReporter(spark);
    }

    /**
     * Starts a new parent test with the given description.
     *
     * @param description The description of the parent test.
     */
    public void parentStartTest(String description) {
        parentTest =reports.createTest(description);
    }

    /**
     * Starts a new child test with the given description and attaches it to the parent test.
     *
     * @param description The description of the child test.
     */
    public void childStartTest(String description) {
       logger =  parentTest.createNode(description);
    }

    /**
     * Closes the current child test.
     */
    public void closeChildTest() throws Exception {
        if (logger != null) {
            reports.flush();
        } else {
            throw new Exception("childTest is null");
        }
    }

    /**
     * Closes the current parent test.
     */
    public void closeParentReports() throws Exception {
        if (parentTest != null) {
            reports.flush();
        } else {
            throw new Exception("parentTest is null");
        }
    }

    /**
     * Navigates to the specified URL.     *
     *
     * @param URL The key representing the URL in the properties file.
     */
    public void navigateToURL(String URL) {
        driver.navigate(prop.getProperty(URL));
    }

    /**
     * Initiates a new browser instance and navigates to the specified URL.     *
     *
     */
    public void invokeBrowser() {
        playWright = Playwright.create();
        if (prop.getProperty("Headless").equalsIgnoreCase("false")) {
            List<String> args = new ArrayList<>();
            args.add("--start-maximized");
            browser = playWright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setArgs(args));
            browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        } else {
            browser = playWright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(true));
            browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1500, 700));
        }
        driver = browserContext.newPage();
        navigateToURL("URL");
    }

    /**
     * Takes a screenshot and returns the filename.
     *
     * @return The filename of the screenshot.
     */
    public String takeScreenShot() {
        String fileName = UUID.randomUUID() + ".png";
        driver.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(reportLocation + fileName)).setFullPage(true));
        return fileName;
    }

    @BeforeSuite
    public void setUp() throws IOException {
        initialize();
        initialiseReports();
    }

    @AfterSuite
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (driver != null) {
            driver.close();
        }
    }
}