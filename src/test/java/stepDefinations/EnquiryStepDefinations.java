package stepDefinations;

import context.TestContextSetup;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.EnquiryPage;

public class EnquiryStepDefinations {

    private TestContextSetup testContextSetup;
    private EnquiryPage enquiryPage;
    private static final Logger logger = LogManager.getLogger(EnquiryStepDefinations.class);

    public EnquiryStepDefinations(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }
    
    // Helper method for lazy initialization
    private EnquiryPage getEnquiryPage() {
        if (enquiryPage == null) {
            enquiryPage = testContextSetup.pageObjectManager.getEnquiryPage();
        }
        return enquiryPage;
    }

    @Given("I open the enquiry page {string}")
    public void i_open_the_enquiry_page(String url) {
        logger.info("Navigating to enquiry page: {}", url);
        testContextSetup.driver.get(url);
    }

    @Given("I am on the enquiry page")
    public void i_am_on_the_enquiry_page() {
        String url = testContextSetup.configFileReader.getProperty("enquiryPageUrl");
        logger.info("Navigating to enquiry page: {}", url);
        testContextSetup.driver.get(url);
    }

    @Then("I should see {string} text")
    public void i_should_see_text(String expectedText) {
        logger.info("Verifying text '{}' is present on the page", expectedText);
        Assert.assertTrue(testContextSetup.driver.getPageSource().contains(expectedText), "Text not found: " + expectedText);
    }

    @Then("I should see the enquiry form")
    public void i_should_see_the_enquiry_form() {
        logger.info("Verifying enquiry form is displayed");
        // The form is not in a <form> tag, so we find it by a known element inside it, like the h1.
        WebElement formHeader = testContextSetup.driver.findElement(By.xpath("//h1[text()='Enquiry']"));
        Assert.assertTrue(formHeader.isDisplayed(), "Enquiry form header not found");
    }
    
    @Then("I should see {string} field")
    public void i_should_see_field(String fieldName) {
        logger.info("Checking for field: {}", fieldName);
        WebElement fieldLabel = testContextSetup.driver.findElement(By.xpath("//label[text()='"+fieldName+"']"));
        Assert.assertTrue(fieldLabel.isDisplayed(), fieldName + " field label not found.");
    }

    @Then("I should see {string} button")
    public void i_should_see_button(String buttonName) {
        logger.info("Checking for button: {}", buttonName);
        WebElement button = testContextSetup.driver.findElement(By.xpath("//button[text()='"+buttonName+"']"));
        Assert.assertTrue(button.isDisplayed(), buttonName + " button not found.");
    }

    @When("I enter {string} in full name field")
    public void i_enter_in_full_name_field(String name) {
        logger.info("Entering name: {}", name);
        getEnquiryPage().enterName(name);
    }

    @When("I enter {string} in email field")
    public void i_enter_in_email_field(String email) {
        logger.info("Entering email: {}", email);
        getEnquiryPage().enterEmail(email);
    }

    @When("I enter {string} in message field")
    public void i_enter_in_message_field(String message) {
        logger.info("Entering message: {}", message);
        getEnquiryPage().enterMessage(message);
    }

    @When("I click send button")
    public void i_click_send_button() {
        logger.info("Clicking send button");
        getEnquiryPage().clickSubmit();
    }
    
    @Then("enquiry should be submitted successfully")
    public void enquiry_should_be_submitted_successfully() {
        logger.info("Verifying enquiry submission");
        // A more robust check for a specific success message.
        Assert.assertTrue(getEnquiryPage().getSuccessMessage().contains("Thank you! We will get back to you as soon as possible."), "Success message not found.");
    }
    
    @Then("I should see error for name field")
    public void i_should_see_error_for_name_field() {
        logger.info("Verifying error for name field");
        Assert.assertTrue(getEnquiryPage().isNameErrorVisible(), "Name error message was not visible.");
    }
    
    @Then("I should see error for email field")
    public void i_should_see_error_for_email_field() {
        logger.info("Verifying error for email field");
        Assert.assertTrue(getEnquiryPage().isEmailErrorVisible(), "Email error message was not visible.");
    }

    @Then("I should see error for message field")
    public void i_should_see_error_for_message_field() {
        logger.info("Verifying error for message field");
        Assert.assertTrue(getEnquiryPage().isMessageErrorVisible(), "Message error message was not visible.");
    }

    @Then("I should see validation errors")
    public void i_should_see_validation_errors() {
        logger.info("Verifying that validation errors are present");
        // Check that all three specific error messages are displayed.
        Assert.assertTrue(getEnquiryPage().isNameErrorVisible(), "Name error was not visible.");
        Assert.assertTrue(getEnquiryPage().isEmailErrorVisible(), "Email error was not visible.");
        Assert.assertTrue(getEnquiryPage().isMessageErrorVisible(), "Message error was not visible.");
    }
}