package hooks;

import context.TestContextSetup;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.PageObjectManager;

import java.time.Duration;

public class Setup {

    private TestContextSetup testContextSetup;
    private static final Logger logger = LogManager.getLogger(Setup.class);

    public Setup(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        String browserName = testContextSetup.configFileReader.getProperty("browserName");
        String driverPath = testContextSetup.configFileReader.getProperty("driverPath");

        switch (browserName.toLowerCase()) {
            case "chrome":
            	System.setProperty("webdriver.chrome.driver", driverPath);
                testContextSetup.driver = new ChromeDriver();
                break;
            case "firefox":
                testContextSetup.driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        testContextSetup.driver.manage().window().maximize();
        testContextSetup.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Initialize PageObjectManager with the created driver
        testContextSetup.pageObjectManager = new PageObjectManager(testContextSetup.driver);
        logger.info("WebDriver initialized successfully for browser: {}", browserName);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (testContextSetup.driver != null) {
            if (scenario.isFailed()) {
                logger.error("Scenario failed: '{}'. Capturing screenshot.", scenario.getName());
                // Attach screenshot to Extent Report
                final byte[] screenshot = ((TakesScreenshot) testContextSetup.driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "screenshot");
            } else {
                logger.info("Scenario passed: '{}'", scenario.getName());
            }
            logger.info("Closing WebDriver.");
            testContextSetup.driver.quit();
        }
    }
}
