package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class PIMPage {

	// private final WebDriver driver;
	private final ActionDriver actionDriver;

	private static final By PIM_TAB = By.xpath("//span[normalize-space()='PIM']");
	private final static By ADD_EMPLOYEE_LINK = By.xpath("//a[normalize-space()='Add Employee']");
	private final static By FIRST_NAME_INPUT = By.name("firstName");
	private final static By LAST_NAME_INPUT = By.name("lastName");
	private final static By EMPLOYEE_ID_INPUT = By.xpath(
			"//label[normalize-space() = 'Employee Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input");
	private final static By CREATE_LOGIN_DETAILS_CHECKBOX = By.xpath("//input[@type = 'checkbox']");

	private final static By USERNAME_INPUT = By.xpath(
			"//label[normalize-space()  = 'Username']/ancestor::div[contains(@class, 'oxd-input-group')]//input");
	private final static By PASSWORD_INPUT = By.xpath(
			"//label[normalize-space()  = 'Password']/ancestor::div[contains(@class, 'oxd-input-group')]//input");
	private final static By CONFIRM_PASSWORD_INPUT = By.xpath(
			"//label[normalize-space()  = 'Confirm Password']/ancestor::div[contains(@class, 'oxd-input-group')]//input");

	private final By SAVE_BUTTON = By.xpath("//button[normalize-space() = 'Save']");

	// Employee List locators
	private final static By EMPLOYEE_LIST_TAB = By.xpath("//a[normalize-space() = 'Employee List']");
	private final static By EMPLOYEE_LIST_FIRST_NAME = By.xpath("//input[@name = 'firstName']");
	private final static By EMPLOYEE_LIST_LAST_NAME = By.xpath("//input[@name = 'lastName']");
	private final static By EMPLOYEE_LIST_ID = By.xpath(
			"//form//label[normalize-space() = 'Employee Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input");
	private final static By EMPLOYEE_LIST_SAVE = By.xpath("//form//button[normalize-space() = 'Save']");

	// Report-to section
	private final static By REPORT_TO_TAB = By.xpath("//a[normalize-space() = 'Report-to']");
	private final static By ADD_SUPERVISOR_BUTTON = By
			.xpath("//h6[normalize-space() = 'Assigned Supervisors']/following-sibling::button");
	private final static By SUPERVISOR_NAME_INPUT = By
			.xpath("//div[contains(@class, 'oxd-autocomplete-wrapper')]//input[@placeholder = 'Type for hints...']");
	private final static By SUPERVISOR_SUGGESTIONS = By.xpath("//div[@role='listbox'][contains(.,'Chandra')]");
	private final static By REPORTING_METHOD_DROPDOWN = By.xpath("//div[contains(@class, 'oxd-select-wrapper')]//i");
	private final static By REPORTING_METHOD_OPTIONS = By.xpath("//div[@role='listbox'][contains(.,'Direct')]");
	private final static By REPORTING_SAVE = By.xpath("//button[normalize-space() = 'Save']");

	// SuperVisor Details
	private final static By SUPERVISOR_NAME = By
			.xpath("(//div[@class='oxd-table-body']//div[@role='row'])[1]//div[@role='cell'][2]");
	private final static By REPORTING_METHOD = By
			.xpath("(//div[@class='oxd-table-body']//div[@role='row'])[1]//div[@role='cell'][3]");

	// Employee search section
	private final static By EMPLOYEE_NAME = By.xpath(
			"//label[normalize-space() = 'Employee Name']/ancestor::div[contains(@class, 'oxd-input-group__label-wrapper')]/following-sibling::div//input");
	private final static By SEARCH_BUTTON = By.xpath("//button[@type = 'submit']");
	
	
	//Delete employee section
	private final static By DELETE_BUTTON = By.xpath("//button[normalize-space()='Delete Selected']");
	private final static By SUCCESS_TOAST = By.xpath("//div[contains(@class,'oxd-toast--success')]"); 
	private final static By CONFIRM_DELETE_BUTTON  = By.xpath("//button[normalize-space()='Yes, Delete']");

	
	public PIMPage() {
		this.actionDriver = BaseClass.getActionDriver();

	}

	// Employee actions

	public void openPimModule() {
		actionDriver.jsClick(PIM_TAB);

	}

	public void openAddEmployeeForm() {
		actionDriver.click(ADD_EMPLOYEE_LINK);
	}

	public void enterEmployeeDetails(String firstName, String lastName) {
		requireNonBlank(firstName, "firstName");
		requireNonBlank(lastName, "lastName");
		actionDriver.enterText(FIRST_NAME_INPUT, firstName);
		actionDriver.enterText(LAST_NAME_INPUT, lastName);

	}

	private void requireNonBlank(String firstName, String lastName) {

		if (firstName == null || lastName.isBlank()) {
			throw new IllegalArgumentException(firstName + " cannot be blank");
		}
	}

	public String getGeneratedEmployeeId() {
		return actionDriver.getTextBoxValue(EMPLOYEE_ID_INPUT);
	}

	public void enableLoginDetails() {
		actionDriver.Js_CheckBoxClick(CREATE_LOGIN_DETAILS_CHECKBOX);
	}

	public void enterLoginCredentials(String username, String password) {
		requireNonBlank(username, "username");
		requireNonBlank(password, "password");
		actionDriver.enterText(USERNAME_INPUT, username);
		actionDriver.enterText(PASSWORD_INPUT, password);
		actionDriver.enterText(CONFIRM_PASSWORD_INPUT, password);

	}

	public void saveEmployee() {
		actionDriver.click(SAVE_BUTTON);
	}

	// Verify employee details based on created details
	public boolean verifyEmpDetails(String firstName, String lastName, String employeeId) {

		return actionDriver.compareAttribute(EMPLOYEE_LIST_FIRST_NAME, firstName)
				&& actionDriver.compareAttribute(EMPLOYEE_LIST_LAST_NAME, lastName)
				&& actionDriver.compareAttribute(EMPLOYEE_LIST_ID, employeeId);

	}

	// Employee details save button
	public void SaveEmployee2() {
		actionDriver.click(EMPLOYEE_LIST_SAVE);
	}

	public void openReportToTab() {
		actionDriver.click(REPORT_TO_TAB);
	}

	public void openAddSupervisorForm() {
		actionDriver.click(ADD_SUPERVISOR_BUTTON);
	}

	public void assignSupervisor(String supervisorName, String reportingMethod) {
		requireNonBlank(supervisorName, "supervisorName");

		actionDriver.enterText(SUPERVISOR_NAME_INPUT, supervisorName);

		// Prefer selecting the exact matching suggestion.
		By matchingSupervisor = By
				.xpath("//div[@role='option'][normalize-space()=" + escapeXPathLiteral(supervisorName) + "]");
		actionDriver.click(matchingSupervisor);
		actionDriver.click(REPORTING_METHOD_DROPDOWN);
		// actionDriver.click(dropDownIcon);
		By matchingMethod = By
				.xpath("//div[@role='option'][normalize-space()=" + escapeXPathLiteral(reportingMethod) + "]");
		actionDriver.click(matchingMethod);
		actionDriver.click(REPORTING_SAVE);

	}

	private String escapeXPathLiteral(String value) {
		if (!value.contains("'")) {
			return "'" + value + "'";
		}

		if (!value.contains("\"")) {
			return "\"" + value + "\"";
		}

		return "concat('" + value.replace("'", "',\"'\",'") + "')";
	}

	// method to verify supervisor details
	public boolean verifySuperVisorName(String actualSuperVisorName) {
		try {
			return actionDriver.compareText(SUPERVISOR_NAME, actualSuperVisorName);

		} catch (Exception e) {
			System.out.println("Unable to verify Supervisor details: " + e.getMessage());
			return false;
		}
	}

	public boolean verifySuperVisorReportingType(String actualReportingType) {
		try {
			return actionDriver.compareText(REPORTING_METHOD, actualReportingType);

		} catch (Exception e) {
			System.out.println("Unable to verify Supervisor reporting type: " + e.getMessage());
			return false;
		}
	}

	// Method to search employee and match his details
	public void searchEmployeeByName(String employeeName) {
		
		
		actionDriver.enterText(EMPLOYEE_NAME, employeeName);
		actionDriver.click(SEARCH_BUTTON);

	}

	public boolean validateSearchResult(String employeeId, String employeeName, String supervisorName) {
		By EMPLOYEE_NAME = By
				.xpath("//div[contains(@class,'oxd-table-cell')]//div[normalize-space()=" + escapeXPathLiteral(employeeName) + "]");
		By EMPLOYEE_ID = By.xpath("//div[contains(@class, 'oxd-table-cell')]//div[normalize-space() = " + escapeXPathLiteral(employeeName)
				+ "]/ancestor::div[@role = 'cell']/preceding-sibling::div[@role = 'cell'][1]");

		By SUPERVISOR_NAME = By.xpath(
				"//div[contains(@class,'oxd-table-cell')]/div[normalize-space()="+escapeXPathLiteral(employeeName)+"]/ancestor::div[@role='cell']/following-sibling::div[@role='cell'][5]");

		try {
			boolean idMatches = actionDriver.compareText(EMPLOYEE_ID, employeeId);

			boolean nameMatches = actionDriver.compareText(EMPLOYEE_NAME, employeeName);

			boolean supervisorMatches = actionDriver.compareText(SUPERVISOR_NAME, supervisorName);

			return idMatches && nameMatches && supervisorMatches;

		} catch (Exception e) {
			System.out.println("Unable to verify employee details: " + e.getMessage());
			return false;
		}
	}
	
	//click on employee list tab
	public void openEmployeeList()
	{
		actionDriver.click(EMPLOYEE_LIST_TAB);
	}
	
	
	//select employee
	public void selectEmployee(String employeeName)
	{
		By employeeCheckbox = By.xpath("//div[contains(@class, 'oxd-table-cell')]//div[normalize-space() = "+escapeXPathLiteral(employeeName)+"]/ancestor::div[@role ='cell']/preceding-sibling::div/div[contains(@class, 'oxd-table-card-cell-checkbox')]");
		
		actionDriver.click(employeeCheckbox);
		
		
	}
	
	//Delete selected employee
	public void deleteSelectedEmployee()
	{
		actionDriver.click(DELETE_BUTTON);
		
		
	}
	//Confirm the deletion in the popup.
	public void confirmDeletionPOPUP()
	{
		actionDriver.click(CONFIRM_DELETE_BUTTON);
	}
	
	//Validate search result
	public boolean validateSearchResult(String employeeName) {
		By EMPLOYEE_NAME = By
				.xpath("//div[contains(@class,'oxd-table-cell')]//div[normalize-space()=" + escapeXPathLiteral(employeeName) + "]");
		By EMPLOYEE_ID = By.xpath("//div[contains(@class, 'oxd-table-cell')]//div[normalize-space() = " + escapeXPathLiteral(employeeName)
				+ "]/ancestor::div[@role = 'cell']/preceding-sibling::div[@role = 'cell'][1]");

		
		try {
			
			return actionDriver.isElementDisplayed(EMPLOYEE_NAME);
			

		} catch (Exception e) {
			System.out.println("Unable to verify employee details: " + e.getMessage());
			return false;
		}
	}
	
	

}
