package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void unauthorisedAccessTest() {
		// Test that verifies that an unauthorized user can only access the login and signup pages.
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void signupLoginLogoutTest() throws InterruptedException {
		// Test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies
		// that the home page is no longer accessible.

		// Register
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login and check Homepage title
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(1000);
		// Logout
		homePage.clickLogout();

		// Verify that the home page is no longer accessible.
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Notes tests
	@Test
	public void createNoteAndDisplayTest() throws InterruptedException {
		// Test that creates a note, and verifies it is displayed.

		// Register
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add Note
		homePage.addNote();
		driver.get("http://localhost:" + this.port + "/");

		// Check note is displayed in the front end
		Assertions.assertEquals(true, homePage.checkNewNoteIsDisplayed());

	}

	@Test
	public void editNoteAndDisplayTest() throws InterruptedException {
		// Test that edits an existing note and verifies that the changes are displayed.

		// Register user
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add Note
		homePage.addNote();
		driver.get("http://localhost:" + this.port + "/");

		// Check new note

		homePage.clickNotesTab();

		Assertions.assertEquals("Test Note", homePage.getRowNoteTitle());
		Assertions.assertEquals("Test note description", homePage.getRowNoteDescription());

		// Edit note

		homePage.editNote();

		// Check edited note
		driver.get("http://localhost:" + this.port + "/");
		homePage.clickNotesTab();

		Assertions.assertEquals("Updated Test Note", homePage.getRowNoteTitle());
		Assertions.assertEquals("Updated test note description", homePage.getRowNoteDescription());

	}

	@Test
	public void deleteNoteTest() throws InterruptedException {
		// Test that deletes a note and verifies that the note is no longer displayed.

		// Register user
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add Note
		homePage.addNote();
		driver.get("http://localhost:" + this.port + "/");

		// Check note is displayed in the front end
		Assertions.assertEquals(true, homePage.checkNewNoteIsDisplayed());

		// Delete note
		homePage.deleteNote();
		driver.get("http://localhost:" + this.port + "/");

//		Check note has been deleted
		Assertions.assertEquals(true, homePage.checkNoteIsDeleted());

	}


	// Credentials tests
	@Test
	public void createAndVerifyCredentials() throws InterruptedException {
		//	Test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.

		// Register user
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add credentials
		homePage.addCredentials();

		// Verify credentials are displayed
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals(true, homePage.checkCredentialsAreDisplayed());

		// Verify that the password is encrypted
		Assertions.assertEquals(true, homePage.checkPasswordIsEncrypted());

	}

	@Test
	public void editAndVerifyCredentials() throws InterruptedException {
		//	Test that views an existing set of credentials, verifies that the viewable password is unencrypted,
		//	edits the credentials, and verifies that the changes are displayed.

		// Register user
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add credentials
		homePage.addCredentials();

		// Verify credentials are displayed
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals(true, homePage.checkCredentialsAreDisplayed());

		// Verify that password is unencrypted in modal
		Assertions.assertEquals(true, homePage.checkPasswordUnencryptedInModal());

		// Edit existing credentials
		homePage.editCredentials();
		driver.get("http://localhost:" + this.port + "/");
		// Verifies that the changes are displayed.
		homePage.checkEditedCredentials();
	}

	@Test
	public void createAndDeleteCredentials() throws InterruptedException {
		//	Test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
		//	password is encrypted.

		// Register user
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup();

		// Login
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login();

		// Add credentials
		homePage.addCredentials();

		// Verify credentials are displayed
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals(true, homePage.checkCredentialsAreDisplayed());

		// Delete credentials
		homePage.deleteCredentials();

		//Check credentials are no longer displayed
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals(true, homePage.checkCredentialsAreDeleted());

	}

}












