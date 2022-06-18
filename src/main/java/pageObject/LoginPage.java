package pageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Page Object class that models the application login page
 * 
 * @author ionutstan
 *
 */
public class LoginPage {

	@FindBy(how = How.NAME, using = "email")
	private WebElement txtEmail;

	@FindBy(how = How.NAME, using = "password")
	private WebElement txtPassword;

	@FindBy(how = How.LINK_TEXT, using = "Forgotten Password")
	private WebElement lnkForgotPassword;

	@FindBy(how = How.XPATH, using = "//input[@value = 'Login']")
	private WebElement btnLogin;

	@FindBy(how = How.XPATH, using = "//div[@id = 'account-login']/div")
	private WebElement lblLoginValidationMsg;

	/**
	 * Performs login action using the provided credentials
	 * 
	 * @param email
	 *        the email address to be used in when logging in
	 * @param password
	 *        the password to be used in when logging in
	 */
	public void login(String email, String password) {

		txtEmail.clear();
		txtEmail.sendKeys(email);
		txtPassword.clear();
		txtPassword.sendKeys(password);
		btnLogin.click();
	}

	/**
	 * Grabs the validation message displayed on the UI when the login fails
	 * 
	 * @return the validation message as displayed on the UI
	 */
	public String getLoginValidationMsg() {

		return lblLoginValidationMsg.getText();
	}

}
