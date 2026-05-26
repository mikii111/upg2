package se.uppgift2.tests;

import se.uppgift2.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

class LoginTests {
    private static final String STANDARD_USER = "standard_user";
    private static final String CORRECT_PASSWORD = "secret_sauce";
    private static final String INVALID_USERNAME = "fel_anvandarnamn";
    private static final String INVALID_PASSWORD = "fel_losenord";
    private static final String EXPECTED_ERROR = "Epic sadface: Username and password do not match any user in this service";

    private WebDriver driver;
    private LoginPage loginPage;

    @RegisterExtension
    TestWatcher logWatcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            System.out.println("PASS: " + context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("FAIL: " + context.getDisplayName() + " - " + cause.getMessage());
        }
    };

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("START: " + testInfo.getDisplayName());

        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv("CI")) || Boolean.parseBoolean(System.getenv("HEADLESS"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginSucceedsWithCorrectCredentials_G() {
        loginPage.login(STANDARD_USER, CORRECT_PASSWORD);

        Assertions.assertTrue(
                loginPage.isOnInventoryPage(),
                "Användaren ska loggas in och hamna på startsidan"
        );
    }

    @Test
    void loginFailsWithWrongUsername_VG() {
        loginPage.login(INVALID_USERNAME, CORRECT_PASSWORD);

        Assertions.assertEquals(
                EXPECTED_ERROR,
                loginPage.getErrorText(),
                "Felmeddelande ska visas vid fel användarnamn"
        );
    }

    @Test
    void loginFailsWithWrongPassword_VG() {
        loginPage.login(STANDARD_USER, INVALID_PASSWORD);

        Assertions.assertEquals(
                EXPECTED_ERROR,
                loginPage.getErrorText(),
                "Felmeddelande ska visas vid fel lösenord"
        );
    }
}