package base;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Before;
import utils.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;

public class BaseTest {

    protected WebDriver driver;
    protected DriverFactory driverFactory;
    public static Logger log = LogManager.getLogger(BaseTest.class);
    private static Map<String, Map<String, String>> credentials;

    @Before
    public void setup() {
        ConfigManager.loadProperties("qa");
        driverFactory = DriverFactory.getInstance();
        driver = driverFactory.initDriver(getBrowserType());
        log.info("Test setup completed for browser: " + getBrowserType());
    }

    @Before
    public void testDataSetup() {
        log.info("Loading test data from credentials.json");
        credentials = loadCredentials(); // Store the result in the class-level variable
        if (credentials != null) {
            log.info("Test data loaded successfully.");
        } else {
            log.error("Failed to load test data.");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Test failed: " + scenario.getName());
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            log.info("Screenshot attached to report for failed scenario: " + scenario.getName());
            captureScreenshot(scenario.getName());
        }
        driverFactory.closeDriver();
        log.info("Test teardown completed.");
    }

    protected String getBrowserType() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "CHROME"; // Default to Chrome if not specified
        }
        return browser.toUpperCase();
    }

    // Method to load the credentials from the credentials.json file
    public static Map<String, Map<String, String>> loadCredentials() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Map and return it
            File file = new File("src/test/resources/testdata/credentials.json");
            Map<String, Map<String, String>> credentialsData = 
                    mapper.readValue(file, Map.class);
            log.info("Credentials loaded successfully.");
            return credentialsData; // Return the parsed credentials
        } catch (Exception e) {
            log.error("Error loading credentials.json", e);
        }
        return null;
    }

    // Getter for credentials
    public static Map<String, String> getCredentialsForRole(String role) {
        if (credentials == null) {
            log.error("Credentials not loaded. Ensure loadCredentials() is called.");
            return null;
        }
        return credentials.get(role); // Return the credentials for the given role
    }
    //Method to capture screenshot
    public void captureScreenshot(String scenarioName) {
        if (driver instanceof TakesScreenshot) {
            // Create the screenshots directory if it doesn't exist
            File screenshotDir = new File(System.getProperty("user.dir")+"/src/test/screenshots/");
            //File screenshotDir = new File("C:/Development/Selenium/bdd-parallel-test/src/test/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdir();
            }
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String destination = System.getProperty("user.dir")+"/src/test/screenshots/" + scenarioName + ".png";
            try {
                FileUtils.copyFile(source, new File(destination));
                log.info("Screenshot taken: " + destination);
            } catch (IOException e) {
                log.error("Error while saving screenshot", e);
            }
        } else {
            log.error("Driver does not support taking screenshots");
        }
    }
}
