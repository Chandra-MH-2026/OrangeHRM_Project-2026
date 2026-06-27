package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManger;

public class DummyTest2 extends BaseClass {
	
	@Test
	public void testDummy()
	{
		// ExtentManger.startTest("Dummy Test 2"); --Implemented in TestListeners, so no need to start test here
		ExtentManger.logStep("Executing dummy test to verify ExtentReports integration.");
		String title = getDriver().getTitle();
		ExtentManger.logStep("Page title retrieved: " + title);
		assert title.equals("OrangeHRM") : "Expected title to be 'OrangeHRM' but was '" + title + "'";
		ExtentManger.logStep("Dummy test passed. Title is: " + title);
		System.out.println("Dummy test passed. Title is: " + title);
	}

}
