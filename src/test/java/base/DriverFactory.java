package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ConfigManager;

import org.openqa.selenium.edge.EdgeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverFactory {

    private static DriverFactory instance;
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    public static Logger log = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() { }

    public static synchronized DriverFactory getInstance() {
        if (instance == null) {
    	    ConfigManager.loadProperties("driver");
            instance = new DriverFactory();
        }
        return instance;
    }

    public WebDriver initDriver(String browserName) {
        BrowserType browser = BrowserType.fromString(browserName);
        String driverPath = ConfigManager.getDriverPath(browserName.toLowerCase());

        switch (browser) {
            case CHROME:
                // Set path to the ChromeDriver executable
                System.setProperty("webdriver.chrome.driver", driverPath );
                driverThread.set(new ChromeDriver());
                log.info("Chrome browser launched.");
                break;
            case FIREFOX:
                // Set path to the GeckoDriver executable
                System.setProperty("webdriver.gecko.driver", driverPath );
                driverThread.set(new FirefoxDriver());
                log.info("Firefox browser launched.");
                break;
            case EDGE:
                // Set path to the EdgeDriver executable
                System.setProperty("webdriver.edge.driver", driverPath );
                driverThread.set(new EdgeDriver());
                log.info("Edge browser launched.");
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driverThread.get().manage().window().maximize();
        log.info("Browser window maximized.");

        return driverThread.get();
    }

    public WebDriver getDriver() {
        return driverThread.get();
    }

    public void closeDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
            log.info("Browser closed.");
        }
    }

    public enum BrowserType {
        CHROME, FIREFOX, EDGE;

        public static BrowserType fromString(String browserName) {
            try {
                return BrowserType.valueOf(browserName.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.error("Invalid browser type! Defaulting to CHROME.");
                return CHROME;
            }
        }
    }
}
