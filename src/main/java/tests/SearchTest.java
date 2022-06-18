package tests;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Class created to showcase a test using geckodriver of Firefox, besides chromedriver, in Selenium
 * 
 * @author stanionut
 *
 */
public class SearchTest {

	/**
	 * Checks the functionality of the search box in Chrome and Firefox
	 */
	@Test
	public void searchBoxTest() {

		System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "./lib/geckodriver.exe");

		WebDriver driverChrome = new ChromeDriver();
		WebDriver driverFirefox = new FirefoxDriver();

		driverChrome.get("http://siit.epizy.com");
		driverFirefox.get("http://siit.epizy.com");

		// ----------------------------------

		WebElement searchBox = driverChrome.findElement(By.name("search"));

		searchBox.sendKeys("macbook", Keys.RETURN);

		WebElement myDynamicElement = (new WebDriverWait(driverChrome, Duration.ofSeconds(3)))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[. = 'Products meeting the search criteria']")));

		System.out.println(myDynamicElement.getText());

		List<WebElement> resultTitleElements = driverChrome.findElements(By.className("product-thumb"));

		System.out.println(resultTitleElements.size());

		for (WebElement we : resultTitleElements) {
			WebElement weText = we.findElement(By.xpath("//div[@class='caption']"));
			System.out.println(weText.getText());
			WebElement resultTitle = we.findElement(By.cssSelector("h4 a"));
			String title = resultTitle.getAttribute("href");
			System.out.println(title);
			System.out.println();
		}

		// ----------------------------------

		WebElement searchBoxFirefox = driverFirefox.findElement(By.name("search"));

		searchBoxFirefox.sendKeys("macbook", Keys.RETURN);

		WebElement myDynamicElementFirefox = (new WebDriverWait(driverFirefox, Duration.ofSeconds(3)))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[. = 'Products meeting the search criteria']")));

		System.out.println(myDynamicElementFirefox.getText());

		List<WebElement> resultTitleElementsFirefox = driverFirefox.findElements(By.className("product-thumb"));

		System.out.println(resultTitleElementsFirefox.size());

		for (WebElement we : resultTitleElementsFirefox) {
			WebElement weText = we.findElement(By.xpath("//div[@class='caption']"));
			System.out.println(weText.getText());
			WebElement resultTitle = we.findElement(By.xpath("//h4/a"));
			String title = resultTitle.getAttribute("href");
			System.out.println(title);
			System.out.println();
		}

		driverChrome.quit();
		driverFirefox.quit();
	}

}
