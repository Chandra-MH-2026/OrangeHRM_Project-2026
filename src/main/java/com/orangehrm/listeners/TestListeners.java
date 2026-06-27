package com.orangehrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManger;
import com.orangehrm.utilities.RetryAnalyzer;

public class TestListeners implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Code to execute when a test starts
		String testName = result.getMethod().getMethodName();
		ExtentManger.startTest(testName);
		ExtentManger.logStep("Test " + testName + " has started.");
	}

	//Triggered when a test method succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		// Code to execute when a test succeeds
		String testName = result.getMethod().getMethodName();
		
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			
			ExtentManger.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully" , " Test " + testName + " has passed.");
			
		}else {
			
			ExtentManger.logStepValidationForAPI(testName + " has passed successfully.");
		}
		
	}

	//Triggered when a test method fails
	@Override
	public void onTestFailure(ITestResult result) {
		// Code to execute when a test fails
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentManger.logStep(failureMessage);
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			
			ExtentManger.logFailure(BaseClass.getDriver(), "Test Failed" , " Test " + testName + " has failed.");
		}else {
			ExtentManger.logFailureForAPI("Test " + testName + " has failed.");
		}
		
	}

	//Triggered when a test method is skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		// Code to execute when a test is skipped
		String testName = result.getMethod().getMethodName();
		ExtentManger.logStep("Test " + testName + " has been skipped.");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Code to execute when a test fails but is within the success percentage
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentManger.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManger.endTest();
	}

}
