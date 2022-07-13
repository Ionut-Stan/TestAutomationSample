package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import core.BaseTests;
import pageObject.LoginPage;
import pageObject.TopMenuPage;

/**
 * Class checks for failed (negative) login tests using the Page Object pattern
 * 
 * @author stanionut
 *
 */
public class LoginTests extends BaseTests {

	static LoginPage loginPage;

	/**
	 * Navigate to login page
	 */
	@BeforeClass
	public static void navigateToLoginPage() {

		TopMenuPage topMenu = new TopMenuPage(driver);
		loginPage = topMenu.navigateToLoginPage();
	}

	/**
	 * Test checks that the error message is displayed to the user when trying to login without filling in the form
	 */
	@Test
	public void emptyCredentialsLoginTest() {

		loginPage.login("", "");

		verifyValidationMessage();
	}

	/**
	 * Test checks that the error message is displayed to the user when trying to login with invalid credentials
	 */
	@Test
	public void invalidCredentialsLoginTest() {

		loginPage.login("dummyEmail@dummy.com", "FakePassword");

		verifyValidationMessage();
	}

	/**
	 * Method checks that error message is correct when user or password are incorrect
	 */
	private static void verifyValidationMessage() {

		String expectedValidationMsg = "Warning: No match for E-Mail Address and/or Password.";
		String actualValidationMsg = loginPage.getLoginValidationMsg();

		Assert.assertEquals("The login validation message was incorrect!", expectedValidationMsg, actualValidationMsg);
	}
}
