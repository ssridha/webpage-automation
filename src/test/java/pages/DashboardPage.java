package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    //Locators
    private By homeLink = By.className("home");
    private By userIcon = By.cssSelector("section#user i.fas.fa-user-circle");
    private By logoutButton = By.xpath("//i[@class='fas fa-sign-out-alt']");

    // Constructor to initialize WebDriver
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public void logout() {
        click(userIcon);
        click(logoutButton);
    }

    // Method to check if the dashboard is displayed
    public boolean isDashboardDisplayed() {
        return isElementDisplayed(homeLink);
    }
}
