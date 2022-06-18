package tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class checks negative scenarios when registration should fail for creating a new account on website using the Boni Garcia WebDriverManager, but not the Page Object pattern. </br>
 * It also creates a valid account.
 *
 * @author ionutstan
 *
 */
public class RegisterTest {

	private static WebDriver driver;
	private static String baseURL = "http://siit.epizy.com";
	private static String firstname = "firstname";
	private static String lastname = "lastname";
	private static String email = "email";
	private static String telephone = "telephone";
	private static String password = "password";
	private static String confirm = "confirm";
	private static String randomEmail = "";

	/**
	 * Open a new fullscreen window in a Chrome browser, on the tested website's homepage
	 */
	@BeforeClass
	public static void initializeBrowser() {

		WebDriverManager.chromedriver()
				.win()
				.setup();

		driver = new ChromeDriver();

		driver.manage()
				.window()
				.maximize();

		driver.get(baseURL);
	}

	/**
	 * Navigates to the Register page
	 */
	private static void navigateToRegisterPage() {

		WebElement myAccountMenu = driver.findElement(By.xpath("//span[. = 'My Account']"));

		myAccountMenu.click();

		WebElement registerMenu = driver.findElement(By.xpath("//a[. = 'Register']"));

		registerMenu.click();
	}

	/**
	 * Clicks <i>Continue</i> button to submit the registration request
	 */
	private static void clickRegisterBtn() {

		WebElement btnRegister = driver.findElement(By.xpath("//input[@value = 'Continue']"));

		btnRegister.click();
	}

	/**
	 * Check the Privacy Policy checkbox
	 */
	private static void chkPrivacyPolicy() {

		WebElement checkboxPrivacyPolicy = driver.findElement(By.xpath("//input[@name = 'agree']"));

		checkboxPrivacyPolicy.click();
	}

	/**
	 * New email address to be used for creating a valid account
	 */
	public static void createRandomEmailAddress() {

		WebDriverManager.chromedriver()
				.win()
				.setup();

		WebDriver driverEmail = new ChromeDriver();

		driverEmail.manage()
				.window()
				.maximize();

		driverEmail.get("https://onlinerandomtools.com/generate-random-string");

		WebElement passwordLength = driverEmail.findElement(By.xpath("//input[@data-index='length']"));
		passwordLength.clear();
		passwordLength.sendKeys("30");

		WebElement numberOfEmailsCreated = driverEmail.findElement(By.xpath("//input[@data-index='count']"));
		numberOfEmailsCreated.clear();
		numberOfEmailsCreated.sendKeys("1");

		WebElement selectTypeOfChar = driverEmail.findElement(By.xpath("//select[@data-index='predefined-charset']"));
		selectTypeOfChar.click();

		WebElement selectTypeOfCharDropdown = driverEmail
				.findElement(By.xpath("//optgroup[@label= 'Predefined Alphabets']/option[@value = 'alphamixnum']"));
		selectTypeOfCharDropdown.click();

		WebElement btnGenerateEmail = driverEmail.findElement(By.xpath("//button[. = 'Generate new random strings']"));
		btnGenerateEmail.click();

		WebElement btnCopyEmail = driverEmail.findElement(By.xpath("//div[@class='widget widget-copy']"));
		btnCopyEmail.click();
		randomEmail = Keys.chord(Keys.LEFT_CONTROL, "v");

		driverEmail.quit();
	}

	/**
	 * Check the Privacy Policy message with all empty fields in registration form
	 */
	@Test
	public void uncheckedPrivacyPolicyTest() {

		navigateToRegisterPage();

		clickRegisterBtn();

		WebElement lblRegisterValidationMsg = driver.findElement(By.xpath("//div[@id = 'account-register']/div"));

		String actualRegistrationMsg = lblRegisterValidationMsg.getText();

		String expectedRegistrationMsg = "Warning: You must agree to the Privacy Policy!";

		Assert.assertEquals("The registration validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);

	}

	/**
	 * Tests if the Privacy Policy error message is visible after only the terms and conditions were checked, and if the user is redirected from registration page (he shouldn't be).
	 */
	@Test
	public void checkedPrivacyPolicyTest() {

		navigateToRegisterPage();

		chkPrivacyPolicy();

		clickRegisterBtn();

		boolean isPresent = driver.findElements(By.xpath("//div[@id = 'account-register']/div[contains(@class, 'alert-danger')]"))
				.size() != 0;

		Assert.assertFalse(isPresent);

		String actualRegistrationURL = driver.getCurrentUrl();

		String expectedSuccessfulRegistrationURL = baseURL + "/index.php?route=account/register";

		Assert.assertEquals("Error! The user should stay on the register page.", expectedSuccessfulRegistrationURL,
				actualRegistrationURL);
	}

	/**
	 * Tests error message when only the first name field is empty
	 */
	@Test
	public void emptyFirstNameTest() {

		navigateToRegisterPage();

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-firstname'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "First Name must be between 1 and 32 characters!";

		Assert.assertEquals("The first name validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when only the last name field is empty
	 */
	@Test
	public void emptyLastNameTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-lastname'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Last Name must be between 1 and 32 characters!";

		Assert.assertEquals("The last name validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when only the email field is empty
	 */
	@Test
	public void emptyEmailTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-email'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "E-Mail Address does not appear to be valid!";

		Assert.assertEquals("The email validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when only the telephone field is empty
	 */
	@Test
	public void emptyTelephoneTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-telephone'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Telephone must be between 3 and 32 characters!";

		Assert.assertEquals("The telephone validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when the telephone field has two digits (minimum is three digits)
	 */
	@Test
	public void twoDigitsTelephoneTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("0");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-telephone'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Telephone must be between 3 and 32 characters!";

		Assert.assertEquals("The telephone validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when only the password field is empty
	 */
	@Test
	public void emptyPasswordTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-password'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Password must be between 4 and 20 characters!";

		Assert.assertEquals("The password validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when the password field has three characters (minimum is four characters)
	 */
	@Test
	public void threeCharactersPasswordTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-password'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Password must be between 4 and 20 characters!";

		Assert.assertEquals("The password validation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Tests error message when the confirm password field does not match the password
	 */
	@Test
	public void confirmPasswordTest() {

		navigateToRegisterPage();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys("dummyEmail2@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("111");

		chkPrivacyPolicy();

		clickRegisterBtn();

		WebElement lblFirstNameValidationMsg = driver.findElement(By.cssSelector("input[id = 'input-confirm'] + div"));

		String actualRegistrationMsg = lblFirstNameValidationMsg.getText();

		String expectedRegistrationMsg = "Password confirmation does not match password!";

		Assert.assertEquals("The password confirmation message was incorrect!", expectedRegistrationMsg, actualRegistrationMsg);
	}

	/**
	 * Creates a valid test account, provided a new email is inserted every time the test is run
	 */
	@Test
	public void createAccountTest() {

		navigateToRegisterPage();

		createRandomEmailAddress();

		WebElement txtFirstName = driver.findElement(By.name(firstname));
		txtFirstName.sendKeys("Test");

		WebElement txtLastName = driver.findElement(By.name(lastname));
		txtLastName.sendKeys("Test");

		WebElement txtEmail = driver.findElement(By.name(email));
		txtEmail.sendKeys(randomEmail + "@dummy.com");

		WebElement txtTelephone = driver.findElement(By.name(telephone));
		txtTelephone.sendKeys("00000");

		WebElement txtPassword = driver.findElement(By.name(password));
		txtPassword.sendKeys("00000");

		WebElement txtConfirmPassword = driver.findElement(By.name(confirm));
		txtConfirmPassword.sendKeys("00000");

		chkPrivacyPolicy();

		clickRegisterBtn();

		String actualRegistrationURL = driver.getCurrentUrl();

		String expectedSuccessfulRegistrationURL = baseURL + "/index.php?route=account/success";

		Assert.assertEquals("The registration was unsuccessful", expectedSuccessfulRegistrationURL, actualRegistrationURL);
	}
}
