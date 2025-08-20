/*
package stepDefinations;

import context.TestContextSetup;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.TicketBookingPage;

import java.time.Duration;

public class TicketBookingSteps {
    private TestContextSetup testContextSetup;
    private TicketBookingPage booking; // Using the variable name 'booking' as requested
    private static final Logger logger = LogManager.getLogger(TicketBookingSteps.class);

    public TicketBookingSteps(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
        // Get the page object from the manager
//        this.booking = testContextSetup.pageObjectManager.getTicketBookingPage();
    }
    
    private TicketBookingPage getBookingPage() {
        if (booking == null) {
            booking = testContextSetup.pageObjectManager.getTicketBookingPage();
        }
        return booking;
    }

    private boolean isConfirmationMessageVisible() {
        try {
            // Check for both visibility and the correct text content
            return booking.bookingConfirmDiv.isDisplayed() &&
                   booking.bookingConfirmDiv.getText().contains("Your Reservation has been Confirmed !");
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @Given("User is on Ticket Booking page")
    public void user_is_on_booking_page() {
        String url = testContextSetup.configFileReader.getProperty("trainBookingPageUrl");
        testContextSetup.driver.get(url);
        logger.info("Navigated to Ticket Booking page: {}", url);
        // Wait to ensure the page is fully loaded before proceeding
        new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(booking.bookNowBtn));
    }

    @When("User leaves required fields blank and clicks Book")
    public void leave_required_fields_blank() {
        logger.info("Leaving all fields blank and clicking Book Now");
        booking.fillBookingForm("", "", "", "", "", "", "", "0");
        booking.clickBookNow();
    }

    @Then("Error messages should be displayed")
    public void validate_required_field_errors() {
        logger.info("Validating that an error message is displayed for blank fields");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));

        // Replicating the detailed wait condition from your original file
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(booking.commonErrorMessage),
            ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "can't be blank"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "required"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "Number of Passengers can't be Zero")
            )
        ));
        String errorMessageText = booking.commonErrorMessage.getText();
        logger.info("Displayed error message: " + errorMessageText);

        Assert.assertTrue(booking.commonErrorMessage.isDisplayed() && !errorMessageText.isEmpty(),
                          "Error message element is not displayed or is empty.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking confirmation message was unexpectedly visible.");
    }

    @When("User enters invalid phone number {string}")
    public void enter_invalid_phone(String phoneNumber) {
        logger.info("Entering invalid phone number: {}", phoneNumber);
        booking.fillBookingForm("Chennai", "Delhi", "31/07/2025", "sleeper", "John", "john@mail.com", phoneNumber, "2");
        booking.clickBookNow();
    }

    @Then("Phone number format validation should trigger")
    public void validate_phone_format() {
        logger.info("Validating phone number format error message");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(booking.commonErrorMessage),
            ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "Phone Number")
        ));
        String errorMessageText = booking.commonErrorMessage.getText();
        logger.info("Displayed error message: " + errorMessageText);

        Assert.assertTrue(errorMessageText.toLowerCase().contains("phone number"),
                          "Phone number validation message incorrect or not displayed. Actual: " + errorMessageText);
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User enters past date {string}")
    public void enter_past_date(String pastDate) {
        logger.info("Entering past date: {}", pastDate);
        booking.fillBookingForm("Chennai", "Delhi", pastDate, "sleeper", "John", "john@mail.com", "9876543210", "2");
        booking.clickBookNow();
    }

    @Then("Date validation message should appear")
    public void validate_date_format() {
        logger.info("Validating past date error message");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(booking.commonErrorMessage),
            ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "Date"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "future")
            )
        ));
        Assert.assertTrue(booking.commonErrorMessage.getText().toLowerCase().contains("date"),
                          "Date validation message for past date incorrect.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User enters invalid string {string} in date field")
    public void user_enters_invalid_string_in_date_field(String invalidDateString) {
        logger.info("Entering invalid date string: {}", invalidDateString);
        booking.fillBookingForm("Chennai", "Delhi", invalidDateString, "sleeper", "John", "john@mail.com", "9876543210", "2");
        booking.clickBookNow();
    }

    @Then("Date validation message should appear for invalid format")
    public void date_validation_message_should_appear_for_invalid_format() {
        logger.info("Validating invalid date format error message");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(booking.commonErrorMessage),
            ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "date"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "format"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "valid")
            )
        ));
        String errorMessage = booking.commonErrorMessage.getText().toLowerCase();
        Assert.assertTrue(errorMessage.contains("date") || errorMessage.contains("format") || errorMessage.contains("valid"),
                          "Date validation message for invalid string incorrect.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User enters invalid passenger count {string}")
    public void user_enters_invalid_passenger_count(String passengerCount) {
        logger.info("Entering invalid passenger count: {}", passengerCount);
        booking.fillBookingForm("Chennai", "Delhi", "31/07/2025", "sleeper", "John", "john@mail.com", "9876543210", passengerCount);
        booking.clickBookNow();
    }

    @Then("Passenger count validation message should appear")
    public void passenger_count_validation_message_should_appear() {
        logger.info("Validating passenger count error message");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(booking.commonErrorMessage),
            ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "passengers"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "count"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "range"),
                ExpectedConditions.textToBePresentInElement(booking.commonErrorMessage, "Zero")
            )
        ));
        String errorMessage = booking.commonErrorMessage.getText().toLowerCase();
        Assert.assertTrue(errorMessage.contains("passengers") || errorMessage.contains("count") || errorMessage.contains("range") || errorMessage.contains("zero"),
                          "Passenger count validation message incorrect.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User fills some details and clicks Reset")
    public void user_fills_some_details_and_clicks_reset() {
        logger.info("Filling form details and clicking Reset");
        booking.fillBookingForm("Hyderabad", "Bangalore", "31/07/2025", "ac", "John", "john@example.com", "9876543210", "3");
        // Wait to ensure form is filled before clicking reset
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeToBe(booking.name, "value", "John"));
        booking.clickReset();
        // Wait for a field to be cleared as a signal that reset is complete
        wait.until(ExpectedConditions.attributeToBe(booking.name, "value", ""));
    }

    @Then("All fields should be cleared")
    public void all_fields_should_be_cleared() {
        logger.info("Verifying all fields are cleared after reset");
        Assert.assertTrue(booking.fromCity.getAttribute("value").isEmpty(), "From City field not cleared.");
        Assert.assertTrue(booking.toCity.getAttribute("value").isEmpty(), "To City field not cleared.");
        Assert.assertTrue(booking.departureDate.getAttribute("value").isEmpty(), "Departure Date field not cleared.");
        Assert.assertTrue(booking.name.getAttribute("value").isEmpty(), "Name field not cleared.");
        Assert.assertTrue(booking.email.getAttribute("value").isEmpty(), "Email field not cleared.");
        Assert.assertTrue(booking.phone.getAttribute("value").isEmpty(), "Phone field not cleared.");
        Assert.assertEquals(booking.passengerCount.getAttribute("value"), "0", "Passenger Count field not reset to 0.");
        Select travelClassSelect = new Select(booking.travelClass);
        Assert.assertEquals(travelClassSelect.getFirstSelectedOption().getAttribute("value"), "", "Travel Class not reset to default.");
    }

    @Then("Booking confirmation should be visible")
    public void booking_success() {
        logger.info("Verifying booking confirmation message");
        WebDriverWait wait = new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(booking.bookingConfirmDiv));
        wait.until(ExpectedConditions.textToBePresentInElement(booking.bookingConfirmDiv, "Your Reservation has been Confirmed !"));
        Assert.assertTrue(isConfirmationMessageVisible(), "Booking confirmation message not found or incorrect.");
        // Also verify that the total amount is visible as a secondary check
        wait.until(ExpectedConditions.visibilityOf(booking.total));
        logger.info("Booking was successful.");
    }
}

*/

package stepDefinations;

import context.TestContextSetup;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.TicketBookingPage;

import java.time.Duration;

public class TicketBookingSteps {
    private TestContextSetup testContextSetup;
    private TicketBookingPage booking;
    private static final Logger logger = LogManager.getLogger(TicketBookingSteps.class);

    public TicketBookingSteps(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }

    // This helper method robustly initializes the page object when first needed.
    private TicketBookingPage getBookingPage() {
        if (booking == null) {
            booking = testContextSetup.pageObjectManager.getTicketBookingPage();
        }
        return booking;
    }
    
    private boolean isConfirmationMessageVisible() {
        try {
            return getBookingPage().bookingConfirmDiv.isDisplayed() &&
                   getBookingPage().bookingConfirmDiv.getText().contains("Your Reservation has been Confirmed !");
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @Given("User is on Ticket Booking page")
    public void user_is_on_booking_page() {
        String url = testContextSetup.configFileReader.getProperty("trainBookingPageUrl");
        testContextSetup.driver.get(url);
        logger.info("Navigated to Ticket Booking page: {}", url);
        new WebDriverWait(testContextSetup.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(getBookingPage().bookNowBtn));
    }

    @When("User leaves required fields blank and clicks Book")
    public void leave_required_fields_blank() {
        logger.info("Leaving all fields blank and clicking Book Now");
        getBookingPage().fillBookingForm("", "", "", "", "", "", "", "0");
        getBookingPage().clickBookNow();
    }

    @Then("Error messages should be displayed")
    public void validate_required_field_errors() {
        logger.info("Validating error messages for blank fields");
        Assert.assertTrue(getBookingPage().commonErrorMessage.isDisplayed(), "Error message element is not displayed.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User enters invalid phone number {string}")
    public void enter_invalid_phone(String phoneNumber) {
        logger.info("Entering invalid phone number: {}", phoneNumber);
        getBookingPage().fillBookingForm("Chennai", "Delhi", "30/07/2025", "sleeper", "John Doe", "john@test.com", phoneNumber, "2");
        getBookingPage().clickBookNow();
    }

    @Then("Phone number format validation should trigger")
    public void validate_phone_format() {
        String errorMessage = getBookingPage().commonErrorMessage.getText();
        logger.info("Validating phone number format error. Actual: {}", errorMessage);
        Assert.assertTrue(errorMessage.toLowerCase().contains("phone number"), "Phone validation message not found.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }
    
    @When("User enters past date {string} in date field")
    public void enter_past_date(String pastDate) {
        logger.info("Entering past date: {}", pastDate);
        getBookingPage().fillBookingForm("Chennai", "Delhi", pastDate, "sleeper", "John Doe", "john@test.com", "9876543210", "2");
        getBookingPage().clickBookNow();
    }

    @Then("Date validation message should appear")
    public void validate_date_format() {
        String errorMessage = getBookingPage().commonErrorMessage.getText();
        logger.info("Validating past date error. Actual: {}", errorMessage);
        Assert.assertTrue(errorMessage.toLowerCase().contains("date"), "Past date validation message not found.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }
    
    @When("User enters invalid string {string} in date field")
    public void user_enters_invalid_string_in_date_field(String invalidDate) {
        logger.info("Entering invalid date string: {}", invalidDate);
        getBookingPage().fillBookingForm("Chennai", "Delhi", invalidDate, "sleeper", "John Doe", "john@test.com", "9876543210", "2");
        getBookingPage().clickBookNow();
    }

    @Then("Date validation message should appear for invalid format")
    public void date_validation_message_should_appear_for_invalid_format() {
        String errorMessage = getBookingPage().commonErrorMessage.getText();
        logger.info("Validating invalid date format error. Actual: {}", errorMessage);
        Assert.assertTrue(errorMessage.toLowerCase().contains("date"), "Invalid date format validation message not found.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }

    @When("User enters invalid passenger count {string}")
    public void user_enters_invalid_passenger_count(String count) {
        logger.info("Entering invalid passenger count: {}", count);
        getBookingPage().fillBookingForm("Chennai", "Delhi", "30/07/2025", "sleeper", "John Doe", "john@test.com", "9876543210", count);
        getBookingPage().clickBookNow();
    }

    @Then("Passenger count validation message should appear")
    public void passenger_count_validation_message_should_appear() {
        String errorMessage = getBookingPage().commonErrorMessage.getText().toLowerCase();
        logger.info("Validating passenger count error. Actual: {}", errorMessage);
        boolean isMessageCorrect = errorMessage.contains("passengers") || errorMessage.contains("count") || errorMessage.contains("zero");
        Assert.assertTrue(isMessageCorrect, "Passenger count validation message not found.");
        Assert.assertFalse(isConfirmationMessageVisible(), "Booking was confirmed unexpectedly.");
    }
    
    @When("User fills some details and clicks Reset")
    public void user_fills_some_details_and_clicks_reset() {
        logger.info("Filling form details and clicking Reset");
        getBookingPage().fillBookingForm("Hyderabad", "Bangalore", "30/07/2025", "ac", "Test User", "test@test.com", "9999999999", "3");
        getBookingPage().clickReset();
    }

    @Then("All fields should be cleared")
    public void all_fields_should_be_cleared() {
        logger.info("Verifying all fields are cleared after reset");
        Assert.assertTrue(getBookingPage().fromCity.getAttribute("value").isEmpty(), "From City not cleared.");
        Assert.assertTrue(getBookingPage().toCity.getAttribute("value").isEmpty(), "To City not cleared.");
        Assert.assertTrue(getBookingPage().name.getAttribute("value").isEmpty(), "Name field not cleared.");
        Assert.assertEquals(getBookingPage().passengerCount.getAttribute("value"), "0", "Passenger Count not reset to 0.");
        Select travelClassSelect = new Select(getBookingPage().travelClass);
        Assert.assertEquals(travelClassSelect.getFirstSelectedOption().getAttribute("value"), "", "Travel Class not reset.");
    }
}