package com.orangehrm.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManger;

public class HomePageTest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homepage;
	
	@BeforeMethod
	public void setupPages(){
		loginpage = new LoginPage();
		homepage = new HomePage();
	}
	
	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void testHomePageElements(String username, String password) {
		//ExtentManger.startTest("Home Page Elements Test"); --Implemented in TestListeners, so no need to start test here
		ExtentManger.logStep("Logging in with valid credentials and verifying home page elements.");
		loginpage.login(username, password);
		ExtentManger.logStep("Logged in successfully, now verifying home page elements.");
		boolean isAdminTabVisible = homepage.isAdminTabDisplayed();
		boolean isLogoVisible = homepage.isOrangeHRMLogoDisplayed();
		
		if (isAdminTabVisible && isLogoVisible) {
			System.out.println("Home page elements are displayed correctly.");
		} else {
			System.out.println("Home page elements are not displayed correctly.");
		}
		ExtentManger.logStep("Admin tab visibility:" + isAdminTabVisible + ", OrangeHRM logo visibility: " + isLogoVisible);
		homepage.logout();
		ExtentManger.logStep("Logged out successfully.");
		staticWait(2000);
	}
	

}
