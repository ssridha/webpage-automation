package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public static Logger log = LogManager.getLogger(BasePage.class);

    // Constructor to initialize WebDriver and WebDriverWait
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        log.info("WebDriver and WebDriverWait initialized.");
    }

    // Extension method to find an element by locator
    protected WebElement findElement(By locator) {
        log.info("Finding element with locator: " + locator);
        return driver.findElement(locator);
    }
    
    // Extension method to click on an element
    public void click(By locator) {
        try {
            log.info("Clicking on element with locator: " + locator);
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            log.info("Element clicked successfully.");
        } catch (Exception e) {
            log.error("Failed to click element with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to enter text in a field
    public void enterText(By locator, String text) {
        try {
            log.info("Entering text '" + text + "' in element with locator: " + locator);
            WebElement element = waitForVisibilityOfElement(locator);
            element.clear();
            element.sendKeys(text);
            log.info("Text entered successfully.");
        } catch (Exception e) {
            log.error("Failed to enter text in element with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to wait for an element to be visible
    public WebElement waitForVisibilityOfElement(By locator) {
        try {
            log.info("Waiting for visibility of element with locator: " + locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.info("Element is visible.");
            return element;
        } catch (Exception e) {
            log.error("Element not visible with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to wait for an element to be clickable
    public WebElement waitForElementToBeClickable(By locator) {
        try {
            log.info("Waiting for element to be clickable with locator: " + locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            log.info("Element is clickable.");
            return element;
        } catch (Exception e) {
            log.error("Element not clickable with locator: " + locator, e);
            throw e;
        }
    }


    // Extension method to check if an element is displayed
    public boolean isElementDisplayed(By locator) {
        try {
            log.info("Checking if element is displayed with locator: " + locator);
            boolean isDisplayed = waitForVisibilityOfElement(locator).isDisplayed();
            log.info("Element display status: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            log.warn("Element not displayed with locator: " + locator, e);
            return false;
        }
    }

    // General reusable methods which can be used in a UI automation framework
    // Extension method to get text from an element
    public String getText(By locator) {
        try {
            log.info("Getting text from element with locator: " + locator);
            String text = waitForVisibilityOfElement(locator).getText();
            log.info("Text retrieved: " + text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get text from element with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to wait for an element to be invisible
    public void waitForElementToBeInvisible(By locator) {
        try {
            log.info("Waiting for element to be invisible with locator: " + locator);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            log.info("Element is now invisible.");
        } catch (Exception e) {
            log.error("Failed to wait for invisibility of element with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to scroll to an element
    public void scrollToElement(By locator) {
        try {
            log.info("Scrolling to element with locator: " + locator);
            WebElement element = waitForVisibilityOfElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            log.info("Scrolled to element successfully.");
        } catch (Exception e) {
            log.error("Failed to scroll to element with locator: " + locator, e);
            throw e;
        }
    }

    // Extension method to get the attribute of an element
    public String getAttribute(By locator, String attribute) {
        try {
            log.info("Getting attribute '" + attribute + "' from element with locator: " + locator);
            String value = waitForVisibilityOfElement(locator).getAttribute(attribute);
            log.info("Attribute value retrieved: " + value);
            return value;
        } catch (Exception e) {
            log.error("Failed to get attribute '" + attribute + "' from element with locator: " + locator, e);
            throw e;
        }
    }
}
