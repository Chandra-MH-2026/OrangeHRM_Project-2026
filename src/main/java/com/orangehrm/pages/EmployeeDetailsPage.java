package com.orangehrm.pages;

import org.openqa.selenium.By;

public class EmployeeDetailsPage {
	
	
	//Employee List tab
		private By employeeList = By.xpath("//a[text() = 'Employee List']");
		
		private By empListEmployeeName = By.xpath("//label[text() = 'Employee Name']/parent::div/following-sibling::div");
		
		private By empListSearchButton = By.xpath("//button[text() = ' Search ']");

}
