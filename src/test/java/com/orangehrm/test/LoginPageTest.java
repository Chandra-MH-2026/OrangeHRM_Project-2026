package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManger;

public class LoginPageTest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homepage;
	
	@BeforeMethod
	public void setupPages(){
		loginpage = new LoginPage();
		homepage = new HomePage();
	}
	
	@Test(
			dataProvider = "validLoginData", 
			dataProviderClass = DataProviders.class
			)
	
	public void testValidLogin(String username, String password) {
		
		//ExtentManger.startTest("Valid Login Test"); --Implemented in TestListeners, so no need to start test here
		ExtentManger.logStep("Navigating to the login page and entering valid credentials.");
		loginpage.login(username, password);
		Assert.assertTrue(homepage.isAdminTabDisplayed(), "Admin tab is not displayed, login might have failed.");
		ExtentManger.logStep("Admin tab is displayed, login successful.");
		homepage.logout();
		ExtentManger.logStep("Logged out successfully.Logged out successfully");
		staticWait(2000);
		
	}
	
	@Test(dataProvider = "inValidLoginData", dataProviderClass = DataProviders.class)
	public void testInvalidLogin(String username, String password) {
		//ExtentManger.startTest("InValid Login Test"); --Implemented in TestListeners, so no need to start test here
		ExtentManger.logStep("Navigating to the login page and entering invalid credentials.");
		loginpage.login(username, password);
		String ExpectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginpage.isErrorMessageDisplayed(), "Error message is not displayed for invalid login.");
		Assert.assertTrue(loginpage.verifyErrorMessageText(ExpectedErrorMessage), "Error message text does not match expected value.");
		ExtentManger.logStep("Error message is displayed with correct text for invalid login.");
	}

}
