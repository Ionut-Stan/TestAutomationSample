package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Class lets all classes who inherit it create a single driver instance for their tests</br>
 * To be used in a Page Object pattern structure
 *
 * @author stanionut
 *
 */
public abstract class BasePageObject {

	protected WebDriver driver;

	public BasePageObject(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
}
