package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	
	ActionDriver actionDriver;
	
	private By adminTab = By.xpath("//img[@alt='client brand banner']");
	private By orangeHRMLogo = By.xpath("//img[@class = 'oxd-userdropdown-img']");
	private By userProfileDropdown = By.xpath("//img[@class = 'oxd-userdropdown-img']");
	private By logoutButton = By.xpath("//a[text() = 'Logout']");
	
	
	private By pimTab = By.xpath("//span[text() = 'PIM']");
	private By employeeSearch = By.xpath("//label[text() = 'Employee Name']/parent::div/following-sibling::div/div/div/input");
	private By searchButton = By.xpath("//button[@type = 'submit']");
	private By employeeFirstandMidname = By.xpath("//div[@class = 'oxd-table-card']/div/div[3]");
	private By employeeLastName = By.xpath("//div[@class = 'oxd-table-card']/div/div[4]");
	
	public HomePage(){
		this.actionDriver = BaseClass.getActionDriver();
		
	}
	
	//Method to check if the Admin tab is displayed on the home page
	public boolean isAdminTabDisplayed() {
		try {
			return actionDriver.isElementDisplayed(adminTab);
		} catch (Exception e) {
			System.out.println("Admin tab not found: " + e.getMessage());
			return false;
		}
	}
	
	//Method to check if the OrangeHRM logo is displayed on the home page
	public boolean isOrangeHRMLogoDisplayed() {
		try {
			return actionDriver.isElementDisplayed(orangeHRMLogo);
		} catch (Exception e) {
			System.out.println("OrangeHRM logo not found: " + e.getMessage());
			return false;
		}
	}
	
	//Method to navigate to PIM tab
	public void navigateToPIMTab() {
		try {
			actionDriver.jsClick(pimTab);
		} catch (Exception e) {
			System.out.println("Unable to navigate to PIM tab: " + e.getMessage());
		}
	}
	
	//Method to search for an employee by name
	public void searchEmployeeByName(String employeeName) {
		try {
			actionDriver.enterText(employeeSearch, employeeName);
			actionDriver.click(searchButton);
			actionDriver.scrollToElement(employeeFirstandMidname);
		} catch (Exception e) {
			System.out.println("Unable to search for employee: " + e.getMessage());
		}
	}
	
	//Verify employee first and middle name
	public boolean verifyEmployeeFirstAndMiddleName(String expectedFirstAndMiddleName) {
		try {
			return actionDriver.compareText(employeeFirstandMidname, expectedFirstAndMiddleName);
		} catch (Exception e) {
			System.out.println("Unable to verify employee first and middle name: " + e.getMessage());
			return false;
		}
	}
	
	//Verify employee last name
	public boolean verifyEmployeeLastName(String expectedLastName) {
		try {
			return actionDriver.compareText(employeeLastName, expectedLastName);
		} catch (Exception e) {
			System.out.println("Unable to verify employee last name: " + e.getMessage());
			return false;
		}
	}
	
	//Method to perform logout action
	public void logout() {
		try {
			
			actionDriver.jsClick(userProfileDropdown);
			actionDriver.click(logoutButton);
		} catch (Exception e) {
			System.out.println("Unable to perform logout: " + e.getMessage());
		}
	}
	
	

}
