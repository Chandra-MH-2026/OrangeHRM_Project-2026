package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManger;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	//protected static WebDriver driver;
	//private static ActionDriver actionDriver;
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	protected static ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
	
	public static SoftAssert getSoftAssert() {
		try {
			return softAssert.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@BeforeSuite
	public void loadProperties() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/main/resources/config.properties");
		prop.load(fis);
		//ExtentManger.getReporter(); // Initialize ExtentReports instance
	}

	@SuppressWarnings("deprecation")
	public synchronized void launchBrowser(String browser) {
		// Set system properties for WebDriver executables based on the browser
		// specified in the properties file
		//String browser = prop.getProperty("browser");
		Boolean seleniumGrid = Boolean.parseBoolean(prop.getProperty("seleniumGrid"));
		String gridURL = prop.getProperty("gridURL");
		
		if(seleniumGrid)
		{
			if (browser.equalsIgnoreCase("chrome")) {
				
				ChromeOptions options = new ChromeOptions();
				
				options.addArguments("--headless=new");
				//options.addArguments("--window-size=2560,1440");
				//options.addArguments("--window-size=1920,1080");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-notifications");
				//options.addArguments("--start-maximized");
				options.addArguments("--ignore-certificate-errors");
				options.addArguments("--disable-popup-blocking");
				//driver = new ChromeDriver();
				//driver.set(new ChromeDriver(options));
				try {
					driver.set(new RemoteWebDriver(new URL(gridURL), options));
				} catch (MalformedURLException e) {
					throw new IllegalArgumentException("Browser not supported: " + browser);
				}
				ExtentManger.registerDriver(getDriver()); // Register WebDriver instance with ExtentManager
				logger.info("RemoteWebDriver Chrome launched successfully.");
				
			} else if (browser.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless=new");
				 options.addArguments("--disable-gpu");
				 options.addArguments("--disable-notifications");
				 options.addArguments("--start-maximized");
				 options.addArguments("--ignore-certificate-errors");
				 options.addArguments("--disable-popup-blocking");
				//driver = new FirefoxDriver();
				//driver.set(new FirefoxDriver(options));
				 try {
						driver.set(new RemoteWebDriver(new URL(gridURL), options));
					} catch (MalformedURLException e) {
						throw new IllegalArgumentException("Browser not supported: " + browser);
					}
				ExtentManger.registerDriver(getDriver());
				logger.info("RemoteWebDriver firefox launched successfully.");
				
			} else if (browser.equalsIgnoreCase("edge")) {
				
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless=new");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-notifications");
				options.addArguments("--start-maximized");
				options.addArguments("--ignore-certificate-errors");
				options.addArguments("--disable-popup-blocking");
				//driver = new EdgeDriver();
				//driver.set(new EdgeDriver(options));
				try {
					driver.set(new RemoteWebDriver(new URL(gridURL), options));
				} catch (MalformedURLException e) {
					throw new IllegalArgumentException("Browser not supported: " + browser);
				}
				ExtentManger.registerDriver(getDriver());
				logger.info("RemoteWebDriver edge launched successfully.");
				
			} else {
				throw new IllegalArgumentException("Browser not supported: " + browser);
			}

		}
		
		else if (browser.equalsIgnoreCase("chrome")) {
			
			ChromeOptions options = new ChromeOptions();
			
			options.addArguments("--headless=new");
			//options.addArguments("--window-size=2560,1440");
			//options.addArguments("--window-size=1920,1080");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-notifications");
			//options.addArguments("--start-maximized");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-popup-blocking");
			
		
			
			
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver(options));
			ExtentManger.registerDriver(getDriver()); // Register WebDriver instance with ExtentManager
			logger.info("Chrome browser launched successfully.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless=new");
			 options.addArguments("--disable-gpu");
			 options.addArguments("--disable-notifications");
			 options.addArguments("--start-maximized");
			 options.addArguments("--ignore-certificate-errors");
			 options.addArguments("--disable-popup-blocking");
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver(options));
			ExtentManger.registerDriver(getDriver());
			logger.info("Firefox browser launched successfully.");
		} else if (browser.equalsIgnoreCase("edge")) {
			
			EdgeOptions options = new EdgeOptions();

			

			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--remote-debugging-port=9222");
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver(options));
			ExtentManger.registerDriver(getDriver());
			logger.info("Edge browser launched successfully.");
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}

	}

	public  void configureBrowser() {

		// Implicit wait
		int implicitWaitTime = Integer.parseInt(prop.getProperty("implicitWait", "10"));
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

		// maximize the browser window
		 driver.get().manage().window().setSize(new Dimension(1920, 1080));

		try {
			// Navigate to the URL specified in the properties file
			driver.get().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Unable to navigate the URL" +e.getMessage());		}
	}
	
	public void staticWait(int seconds) {
		
		LockSupport.parkNanos(seconds);
	}

	@BeforeMethod
	@Parameters("browser")
	public synchronized void  setUp(String browser) throws IOException {
		
		System.out.println("Setting up the webdrvier for the test: " + this.getClass().getSimpleName());
		
		launchBrowser(browser);
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialized and browser Maximized successfully.");
		logger.trace("This is a trace message for debugging purposes.");
		logger.warn("This is a warning message indicating a potential issue.");
		logger.error("This is an error message indicating a failure in the test execution.");
		logger.fatal("This is a fatal message indicating a critical failure in the test execution.");
		logger.debug("This is a debug message providing detailed information for debugging purposes.");
		System.out.println(getDriver().manage().window().getSize());
		
		// Initialize ActionDriver instance after WebDriver is initialized
	/*	if(actionDriver.get() == null) {
			System.out.println("ActionDriver instance is initialized successfully.");
			//actionDriver = new ActionDriver(driver);
			
			
		} else {
			System.out.println("ActionDriver instance is not initialized. Please check the launchBrowser method.");
		} */
		//Initialize ActionDriver instance for current thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver instance is initialized for thread." +Thread.currentThread().getId());

	}
	
	//get properties method to be used in test classes
	public static Properties getProperties() {
		return prop;
	}
	
	//get driver method to be used in test classes
	/*public WebDriver getDriver() {
		return driver;
	}
	*/
	//set driver method to be used in test classes
	/*public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	*/
	
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("WebDriver instance is not initialized. Please check the launchBrowser method.");
			return null;
		}
		return driver.get();
	}
	
	
	public static ActionDriver getActionDriver() {
		
		if(actionDriver.get() == null)
		{
			System.out.println("ActionDriver instance is not initialized. Please check the launchBrowser method.");
			return null;
		}
		return actionDriver.get();
	}

	@AfterMethod
	public synchronized void tearDown() {
		if (driver != null) {
			try {
				driver.get().quit();
			} catch (Exception e) {
				System.out.println("Error occurred while quitting the driver: " + e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed");
		driver.remove(); // Remove WebDriver instance from ThreadLocal to prevent memory leaks
		actionDriver.remove(); // Remove ActionDriver instance from ThreadLocal to prevent memory leaks
		//driver = null; // Reset WebDriver instance after quitting the driver
		//actionDriver = null; // Reset ActionDriver instance after quitting the driver
		//ExtentManger.endTest(); // Flush the ExtentReports after each test method
		
	}

}
