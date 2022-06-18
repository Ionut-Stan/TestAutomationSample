package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import core.BasePageObject;

/**
 * Page Object class that models the application main menu
 * 
 * @author ionutstan
 *
 */
public class TopMenuPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//form[@id='form-currency']")
	private WebElement currency;

	@FindBy(how = How.XPATH, using = "//ul[@class='list-inline']//li[1]//a")
	private WebElement telephone;

	@FindBy(how = How.XPATH, using = "//span[. = 'My Account']")
	private WebElement myAccount;

	@FindBy(how = How.XPATH, using = "//a[. = 'Login']")
	private WebElement login;

	@FindBy(how = How.XPATH, using = "//a[. = 'Register']")
	private WebElement register;

	@FindBy(how = How.ID, using = "wishlist-total")
	private WebElement wishList;

	@FindBy(how = How.LINK_TEXT, using = "Shopping Cart")
	private WebElement shoppingCart;

	@FindBy(how = How.LINK_TEXT, using = "Checkout")
	private WebElement checkout;

	public TopMenuPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Method navigates to the application's login page
	 * 
	 * @return an instance of login page to be used for testing
	 */
	public LoginPage navigateToLoginPage() {

		myAccount.click();
		login.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}
}
