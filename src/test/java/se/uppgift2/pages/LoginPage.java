package se.uppgift2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private static final String URL = "https://www.saucedemo.com/";

    private final WebDriver driver;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By inventoryContainer = By.id("inventory_container");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getErrorText() {
        WebElement error = driver.findElement(errorMessage);
        return error.getText();
    }

    public boolean isOnInventoryPage() {
        boolean correctUrl = driver.getCurrentUrl().contains("/inventory.html");
        boolean inventoryVisible = driver.findElement(inventoryContainer).isDisplayed();
        return correctUrl && inventoryVisible;
    }
}