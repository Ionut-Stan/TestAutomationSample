package core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class lets you create a browser instance
 * 
 * @author stanionut
 *
 */
public class BaseTests {

	protected static WebDriver driver;

	/**
	 * Method opens a new browser window to be used for testing
	 */
	@BeforeClass
	public static void initializeBrowser() {

		driver = getDriver(Browser.CHROME);

		driver.manage()
				.window()
				.maximize();

		String url = "http://siit.epizy.com";
		driver.get(url);
	}

	/**
	 * Method creates an instance of a browser based on a browser name as a parameter
	 * 
	 * @param browser
	 *        name
	 * @return an instance of browser
	 */
	public static WebDriver getDriver(Browser browser) {

		WebDriver driver;

		switch (browser) {
		case CHROME:
			WebDriverManager.chromedriver()
					.win()
					.setup();
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			WebDriverManager.firefoxdriver()
					.win()
					.setup();
			driver = new FirefoxDriver();
			break;
		case IE:
			WebDriverManager.iedriver()
					.win()
					.setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new IllegalArgumentException("Unknown browser: " + browser);
		}

		return driver;
	}

	/**
	 * Method closes every window and destroys the browser instance
	 */
	@AfterClass
	public static void tearDown() {

		driver.quit();
	}
}
