package stepDefinitions;

import base.BaseTest;
import base.DriverFactory;
import io.cucumber.java.en.*;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages.LoginPage;
import pages.DashboardPage;
import utils.ConfigManager;

public class LoginSteps {

    private WebDriver driver = DriverFactory.getInstance().getDriver();
    private LoginPage loginPage = new LoginPage(driver);
    private DashboardPage dashboardPage = new DashboardPage(driver);
    public static Logger Log = LogManager.getLogger(LoginSteps.class);
    private String username;
    private String password;

    @Given("the user is on the login page")
    public void user_is_on_the_login_page() {

        String relativePath = ConfigManager.getProperty("relativePath");

        if (relativePath != null && " " != relativePath){
            String urlToLoad = "file:///"+System.getProperty("user.dir")+"/"+relativePath;
            driver.get(urlToLoad);
        } else{
            Log.error("RelativePath is Empty");
        }
    }

    @When("^the user logs in as \"([^\"]*)\"$")
    public void theUserLogsInAsRole(String role) {
        // Fetch the credentials for the given role from the JSON file
        Map<String, String> credentials = BaseTest.getCredentialsForRole(role);
        if (credentials != null) {
            username = credentials.get("username");
            password = credentials.get("password");

            // Use the username and password to login
            Log.info("Logging in as: " + role);
            Log.info("Username: " + username);
            Log.info("Password: " + password);

            // Enter the username and password into the login form
            loginPage.login(username, password);
        } else {
            Log.info("Failed to retrieve credentials for role: " + role);
        }  
    }

    @When("the user enters invalid username and password")
    public void user_enters_invalid_username_and_password() {
        loginPage.login("invalidUser", "invalidPassword");
    }

    @When("the user leaves the username and password fields blank")
    public void user_leaves_username_and_password_blank() {
        loginPage.login("", "");
    }

    @When("the user clicks on the login button")
    public void user_clicks_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the dashboard")
    public void user_is_redirected_to_dashboard() {
        // Assuming that DashboardPage has a method to check if it's displayed
        boolean isDashboardDisplayed = dashboardPage.isDashboardDisplayed();
        if (!isDashboardDisplayed) {
            throw new AssertionError("User was not redirected to the dashboard.");
        }
    }

    @Then("the user should not be redirected to the dashboard")
    public void user_should_not_be_redirected_to_dashboard() {
        // Add verification logic to ensure user is not redirected
        boolean isDashboardDisplayed = dashboardPage.isDashboardDisplayed();
        if (isDashboardDisplayed) {
            throw new AssertionError("User was redirected to the dashboard when they should not have been.");
        }
    }

    @When("the user clicks on the logout button")
    public void user_clicks_on_the_logout_button() {
        dashboardPage.logout();
    }

    @Then("the user is navigated to login page")
    public void the_user_is_navigated_to_login_page() {
        boolean isHeaderDisplayed = loginPage.isHeaderDisplayed();
        if (!isHeaderDisplayed) {
            throw new AssertionError("User was not redirected to the login page.");
        }
    }
    
}
