package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManger;


public class ActionDriver {
	
	WebDriver driver;
	WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;
	
	
	public ActionDriver(WebDriver driver){
		this.driver = driver;
		int implicitWaitTime = Integer.parseInt(BaseClass.getProperties().getProperty("explicitWait", "10"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(implicitWaitTime));
		logger.info("WebDriver instance is initialized successfully.");
	}
	
//wait for an element to be clickable
	public void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			logger.info("Element is clickable: " + by);
			ExtentManger.logStep( "Element is clickable: " + by);
		} catch (Exception e) {
			//System.out.println("Element not clickable: " + by);
			 logger.error("Element not clickable: " + by, e);
			 ExtentManger.logFailure(BaseClass.getDriver(), "Unable to click an element", by+" is not clickable.");
		}
	}
	
//Wait for an element to be visible
	public void waitForElementToBeVisible(By by) {
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			logger.info("Element is visible: " + by);
		} catch (Exception e) {
			
			System.out.println("Element not visible: " + by);
			 logger.error("Element not visible: " + by, e);
		}
	}
//Method to click on an element
	public void click(By by) {
		try {
			borderElement(by,"Green");
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			 logger.info("Clicked on element: " + by);
			 ExtentManger.logStep("Clicked on element: " + by);
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Unable to click on element: " + by);
			logger.error("Unable to click on element: " + by, e);
			 ExtentManger.logFailure(BaseClass.getDriver(), "Unable to click an element", "Failed to click on element: " + by);
		}
	}
	
//Javascript click method
	public void jsClick(By by) {
		try {
			borderElement(by,"Green");
			waitForElementToBeClickable(by);
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			 logger.info("Clicked on element using JavaScript: " + by);
			 ExtentManger.logStep("Clicked on element: " + by);
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Unable to click on element using JavaScript: " + by);
			 logger.error("Unable to click on element using JavaScript: " + by, e);
			 ExtentManger.logFailure(BaseClass.getDriver(), "Unable to click an element", "Failed to click on element: " + by);
		}
	}
	
//Method to enter text into an element
	public void enterText(By by, String text) {
		try {
			waitForElementToBeVisible(by);
			borderElement(by,"Green");
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(text);
			 logger.info("Entered text '" + text + "' into element: " + by);
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Unable to enter text into element: " + e.getMessage());
			 logger.error("Unable to enter text into element: " + by, e);
		}
	}
	
	
//Method to get text from an element
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			borderElement(by,"Green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Unable to get text from element: " + e.getMessage());
			 logger.error("Unable to get text from element: " + by, e);
			return null;
		}
	}
	
//Method to compare expected and actual text
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = getText(by);
			if (actualText != null && actualText.equals(expectedText)) {
				borderElement(by,"Green");
				System.out.println("Text are matching. Expected: " + expectedText + ", Actual: " + actualText);
				ExtentManger.logStepWithScreenshot(BaseClass.getDriver(), "Compare Text", "Text are matching. Expected: " + expectedText + ", Actual: " + actualText);
				return true;
			} else {
				borderElement(by,"Red");
				System.out.println("Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
				ExtentManger.logFailure(BaseClass.getDriver(), "Compare Text", "Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
				return false;
				
			}
		} catch (Exception e) {
			System.out.println("Unable to compare text: " + e.getMessage());
			logger.error("Unable to compare text for element: " + by, e);
			 return false;
		}
		
	}
	
//method to check if an element is displayed
	public boolean isElementDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			borderElement(by,"Green");
			ExtentManger.logStepWithScreenshot(BaseClass.getDriver(), "Element is displayed", ""+ by + " is displayed on the page.");
			 logger.info("Element is displayed: " + by);
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Element not displayed: " + e.getMessage());
			logger.error("Element not displayed: " + by, e);
			 ExtentManger.logFailure(BaseClass.getDriver(), "Element is not displayed", "Element is not displayed: " + by);
			return false;
		}
	}
	
	//wait for page to load
	public void waitForPageToLoad() {
		try {
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			System.out.println("Page did not load completely: " + e.getMessage());
			logger.error("Page did not load completely", e);
		}
	}
	
	//scroll to an element
	public void scrollToElement(By by) {
		try {
			borderElement(by,"Green");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			
		} catch (Exception e) {
			borderElement(by,"Red");
			System.out.println("Unable to scroll to element: " + by);
			logger.error("Unable to scroll to element: " + by, e);
		}
	}
	
	//Utility method to border an element
	public void borderElement(By by, String color) {
		try {
			String borderColor = "arguments[0].style.border='3px solid " +color+ "'";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript(borderColor, element);
			logger.info("Bordered element: " + by + " with color: " + color);
			 ExtentManger.logStep("Bordered element: " + by + " with color: " + color);
			 Thread.sleep(500); // Pause to make the border visible
			
		} catch (Exception e) {
			System.out.println("Unable to border element: " + by);
			logger.error("Unable to border element: " + by, e);
			 ExtentManger.logFailure(BaseClass.getDriver(), "Unable to border element", "Failed to border element: " + by);
		}
	}
		
}
