package com.orangehrm.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManger {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();
	
	//initialize ExtentReports instance
	public static synchronized ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtendReport/extentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("OrangeHRM Test Automation Report");
			spark.config().setDocumentTitle("OrangeHRM Test Report");
			spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
			extent = new ExtentReports();
			extent.attachReporter(spark);
			//Adding system info to the report
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("Browser", System.getProperty("browser"));
			
			
		}
		return extent;
	}
	
	//Start the test
	public static synchronized ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}
	
	//end the test
	public synchronized static void endTest() {
		getReporter().flush();
	}
	
	//get the current threads test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}
	
	//Method to get the name of the current test
	public static String getCurrentTestName() {
		ExtentTest currentTest = getTest();
		return currentTest != null ? currentTest.getModel().getName() : "Unknown Test";
	}
	
	
	//log a step in the current test
	
	public static void logStep(String logMessage) {
		
		getTest().info(logMessage);
	}
	
	//log a step validation with screenshot
	public static  void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) {
		String coloredMessage = "<span style='color:green; font-weight:bold;'>" + logMessage + "</span>";
		getTest().pass(coloredMessage);
		attachScreenshotToReport(driver, screenshotMessage);
	}
	
	//Log a failure 
	public static  void logFailure(WebDriver driver, String logMessage, String screenshotMessage)
	{
		String coloredMessage = "<span style='color:red; font-weight:bold;'>" + logMessage + "</span>";
		getTest().fail(coloredMessage);
		attachScreenshotToReport(driver, screenshotMessage);
	}
	
	//log a step validation for API without screenshot
		public static  void logStepValidationForAPI(String logMessage) {
			
			String coloredMessage = "<span style='color:green; font-weight:bold;'>" + logMessage + "</span>";
			getTest().pass(coloredMessage);
			
		}
	
	//Log a failure for API without screenshot
		public static  void logFailureForAPI( String logMessage)
		{
			String coloredMessage = "<span style='color:red; font-weight:bold;'>" + logMessage + "</span>";
			getTest().fail(coloredMessage);
			
		}
	
	//log a skip
	public static void logSkip(String logMessage)
	{
		String coloredMessage = "<span style='color:orange; font-weight:bold;'>" + logMessage + "</span>";
		getTest().skip(coloredMessage);
		
		
	}
	
	
	
	
	//take screenshot method
	public static synchronized String takeScreenshot(WebDriver driver, String screenshotName) {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String destination = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + screenshotName + "_"+timestamp+ ".png";
		
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//converting screenshot to base64 format for embedding in the report
		String base64Screenshot = convertScreenshotToBase64(source);
		return base64Screenshot;
		
	}
	
	//Convert screenshot to base64 
	public static synchronized String convertScreenshotToBase64(File screenshotFile) {
		
		String base64Sformat = "";
		byte[] 	fileContent;
		//Read the file content into byte array
		try {
			fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Sformat = Base64.getEncoder().encodeToString(fileContent);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64Sformat;
		
	}
	
	//Attach screenshot to the report using base64 format
	public static synchronized void attachScreenshotToReport(WebDriver driver, String screenshotMessage) {
		
		try {
			String base64Screenshot = takeScreenshot(driver, getCurrentTestName());
			getTest().info(screenshotMessage, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach screenshot: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Register webdriver for the current thread
		public static synchronized void registerDriver(WebDriver driver) {
			long threadId = Thread.currentThread().getId();
			driverMap.put(threadId, driver);
		}
		
	
	

}
