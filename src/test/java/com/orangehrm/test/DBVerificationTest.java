package com.orangehrm.test;

import java.sql.SQLException;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.ExtentManger;
import com.orangehrm.utilities.RetryAnalyzer;

import junit.framework.Assert;

public class DBVerificationTest extends BaseClass {
	
	private static LoginPage loginpage;
	private static HomePage homepage;
	
	
	@BeforeMethod
	public void setupPages(){
		loginpage = new LoginPage(driver.get());
		homepage = new HomePage(driver.get());
	}
	
	@Test()
	public static void employeeDataVerification() throws SQLException {
		
		SoftAssert softAssert = getSoftAssert();
		
		//ExtentManger.startTest("Employee Data Verification from Database Test");
		ExtentManger.logStep("Starting database verification for employee data.");
		
		ExtentManger.logStep("Login to the applicatio using valid credentials.");
		
		loginpage.login("chandrakant", "Chandu@123");
		
		ExtentManger.logStep("Logged in successfully");
		
		homepage.navigateToPIMTab();
		
		ExtentManger.logStep("Clicked on PIM tab to view employee data.");
		
		String employeeName = "Chandra";
		
		ExtentManger.logStep("Searching for employee with name: " + employeeName);
		
		homepage.searchEmployeeByName(employeeName);
		
		ExtentManger.logStep("Get the employee name from DB and compare with the name displayed on the UI.");
		String employee_id = "1";
		
		Map<String, String> employeeDataFromDB = DBConnection.getEmployeeDetails(employee_id);
		
		String empFirstNameFromDB = employeeDataFromDB.get("first_name");
		String empMiddleNameFromDB = employeeDataFromDB.get("middle_name");
		String empLastNameFromDB = employeeDataFromDB.get("last_name");
		
		String FirstAndMidleNameFromDB = (empFirstNameFromDB+" "+empMiddleNameFromDB).trim(); //Trim to handle cases where middle name is null or empty
		
		ExtentManger.logStep("Employee name from DB: " + FirstAndMidleNameFromDB + " " + empLastNameFromDB);
		
		ExtentManger.logStep("Verify employee firstname and middle name from DB with the name displayed on the UI.");
		
		softAssert.assertTrue(homepage.verifyEmployeeFirstAndMiddleName(FirstAndMidleNameFromDB));
		
		ExtentManger.logStep("Verify employee last name from DB with the name displayed on the UI.");
		
		softAssert.assertTrue(homepage.verifyEmployeeLastName(empLastNameFromDB));
		
		ExtentManger.logStep("Employee data verification from DB is successful.");
		
		softAssert.assertAll();
	
		
	}
	
	

}
