package com.orangehrm.test;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManger;

import org.testng.Assert;

public class PIMPageTest extends BaseClass {

	private LoginPage loginpage;
	private HomePage homepage;
	private PIMPage pimPage;

	@BeforeMethod
	public void setupPages() {
		loginpage = new LoginPage();
		homepage = new HomePage();
		pimPage = new PIMPage();
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class, description = "Verify that an employee can be assigned to a supervisor")
	public void assignEmployeeToManager(String username, String password) {
		ExtentManger.logStep("Login to OrangeHRM");
		loginpage.login(username, password);
		ExtentManger.logStep("Logged in successfully");
		ExtentManger.logStep("Open PIM module");
		pimPage.openPimModule();
		ExtentManger.logStep("Clicked on PIM Tab");

		ExtentManger.logStep("Open Add Employee form");
		pimPage.openAddEmployeeForm();

		// Generate unique employee data for repeatable test execution.
		String uniqueId = UUID.randomUUID().toString().substring(0, 8);

		String employeeFirstName = "Rahul" + uniqueId;
		String employeeLastName = "Joshi";

		ExtentManger.logStep("Enter employee details");
		pimPage.enterEmployeeDetails(employeeFirstName, employeeLastName);

		ExtentManger.logStep("Enable login credentials");
		pimPage.enableLoginDetails();

		String employeeUsername = "User_" + uniqueId;
		String employeePassword = "Joshi@123";

		ExtentManger.logStep("Enter employee login credentials");
		pimPage.enterLoginCredentials(employeeUsername, employeePassword);

		// Capture the generated ID before saving the employee.

		ExtentManger.logStep("Employee personal Details page will open");
		String employeeId = pimPage.getGeneratedEmployeeId();

		Assert.assertNotNull(employeeId, "Employee ID should not be null");

		Assert.assertFalse(employeeId.isBlank(), "Employee ID should not be blank");

		Assert.assertTrue(pimPage.verifyEmpDetails(employeeFirstName, employeeLastName, employeeId),
				"Employee details are not as expected");

		ExtentManger.logStep("Save employee details");
		pimPage.saveEmployee();

		ExtentManger.logStep("Open Report-to tab");
		pimPage.openReportToTab();

		ExtentManger.logStep("Open Add Supervisor form");
		pimPage.openAddSupervisorForm();

		String expectedSupervisor = "Chandra Ghasti";
		String expectedReportingMethod = "Direct";

		ExtentManger.logStep("Assign supervisor and reporting method");
		pimPage.assignSupervisor(expectedSupervisor, expectedReportingMethod);
		// again open report to tab after adding assigning supervisor
		pimPage.openReportToTab();

		ExtentManger.logStep("Verify assigned supervisor");

		Assert.assertTrue(pimPage.verifySuperVisorName(expectedSupervisor), "Assigned supervisor name does not match");

		ExtentManger.logStep("Verify reporting method");

		Assert.assertTrue(pimPage.verifySuperVisorReportingType(expectedReportingMethod),
				"Reporting method does not match");

	}
	
	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class, description = "Verify employee delete functionality as admin")
	public void verifyEmployeeSearch(String username, String password) {
	ExtentManger.logStep("Login to OrangeHRM");
		//ExtentManger.startTest("Dummy Test 2");
		loginpage.login(username, password);
		ExtentManger.logStep("Logged in successfully");
		ExtentManger.logStep("Open PIM module");
		pimPage.openPimModule();
		ExtentManger.logStep("Clicked on PIM Tab");
		
		//Search employee
		String employeeName = "Chandra";
		String employee_id = "0014";
		String supervisorName = "Chandra Ghasti";
		pimPage.searchEmployeeByName(employeeName);
		
		
		//Verify result
		
		Assert.assertTrue(pimPage.validateSearchResult(employee_id, employeeName, supervisorName),"Employee not found in search result");
	}
	
	
	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class, description = "Verify employee search functionality as admin")
	public void verifyDeleteEmployee(String username, String password) {
	ExtentManger.logStep("Login to OrangeHRM");
		//ExtentManger.startTest("Dummy Test 2");
		loginpage.login(username, password);
		ExtentManger.logStep("Logged in successfully");
		ExtentManger.logStep("Open PIM module");
		pimPage.openPimModule();
		ExtentManger.logStep("Clicked on PIM Tab");
		
		//Navigate to PIM → Employee List.
		pimPage.openEmployeeList();
		
		String employeeName = "Prashant";
		
		//Search for a test employee.
		pimPage.searchEmployeeByName(employeeName);
		
		//Select the checkbox corresponding to that employee.
		pimPage.selectEmployee(employeeName);
		
		//Click the Delete button.
		pimPage.deleteSelectedEmployee();

		//Confirm the deletion in the popup.
		pimPage.confirmDeletionPOPUP();
		
		//Again Navigate to PIM → Employee List.
		pimPage.openEmployeeList();

		//Search for the same employee again.
		pimPage.searchEmployeeByName(employeeName);
		
		Assert.assertFalse(
		        pimPage.validateSearchResult(employeeName),
		        "Employee still appears after deletion."
		    );

}
}
