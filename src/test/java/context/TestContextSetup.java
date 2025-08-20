package context;

import org.openqa.selenium.WebDriver;
import pages.PageObjectManager;
import utils.ConfigFileReader;

import java.io.IOException;

public class TestContextSetup {

    public WebDriver driver;
    public PageObjectManager pageObjectManager;
    public ConfigFileReader configFileReader;

    public TestContextSetup() {
        configFileReader = new ConfigFileReader();
//        pageObjectManager = new PageObjectManager(driver); // driver is initially null
    }
}