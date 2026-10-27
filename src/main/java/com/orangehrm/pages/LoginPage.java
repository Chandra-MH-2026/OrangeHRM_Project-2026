package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage extends BaseClass {
	
	ActionDriver actionDriver;
	
	private By username = By.name("username");
	private By password = By.xpath("//input[@type = 'password']");
	private By loginButton = By.xpath("//button[text() = ' Login ']");
	private By errorMessage = By.xpath("//p[text() = 'Invalid credentials']");
	
	public LoginPage() { 
		this.actionDriver = BaseClass.getActionDriver();
		
	}
	
	//Method to perform login action
	public void login(String user, String pass) {
		actionDriver.enterText(username, user);
		actionDriver.enterText(password, pass);
		actionDriver.click(loginButton);
	}
	
	//Method to get error message is displayed after failed login attempt
	public boolean isErrorMessageDisplayed() {
		try {
			return actionDriver.isElementDisplayed(errorMessage);
		} catch (Exception e) {
			System.out.println("Error message not found: " + e.getMessage());
			return false;
		}
	}
	
	//Method to get the error message text
	public String getErrorMessageText() {
		try {
			return actionDriver.getText(errorMessage);
		} catch (Exception e) {
			System.out.println("Unable to get error message text: " + e.getMessage());
			return "";
		}
	}
	
	//method to verify error message text
	public boolean verifyErrorMessageText(String expectedText) {
		try {
			return actionDriver.compareText(errorMessage, expectedText);
		} catch (Exception e) {
			System.out.println("Unable to verify error message text: " + e.getMessage());
			return false;
		}
	}
	
	
}
