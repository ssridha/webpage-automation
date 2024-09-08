package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import base.BasePage;

public class LoginPage extends BasePage {

    // Locators
    private By usernameField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//input[@value='LOGIN']");
    private By headerText = By.tagName("h1");

    // Constructor to initialize WebDriver
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public void login(String username, String password) {
        enterText(usernameField, username);
        enterText(passwordField, password);
        
    }
    public void clickLoginButton()
    {
    	click(loginButton);
    }
    public boolean isHeaderDisplayed() {
        return isElementDisplayed(headerText);
    }
}
