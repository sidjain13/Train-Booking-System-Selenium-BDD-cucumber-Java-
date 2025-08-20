package stepDefinations;

import context.TestContextSetup;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import pages.LoginPage;
import pages.ResetPanelPage;
import pages.SignupPage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginStepDefinitions {

    private TestContextSetup testContextSetup;
    private LoginPage loginPf;
    private SignupPage signupPf;
    private ResetPanelPage resetPanelPf;
    private String parentWindowHandle;
    private static final Logger logger = LogManager.getLogger(LoginStepDefinitions.class);

    public LoginStepDefinitions(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }

    private LoginPage getLoginPage() {
        if (loginPf == null) {
            loginPf = testContextSetup.pageObjectManager.getLoginPage();
        }
        return loginPf;
    }

    private ResetPanelPage getResetPanelPage() {
        if (resetPanelPf == null) {
            resetPanelPf = testContextSetup.pageObjectManager.getResetPanelPage();
        }
        return resetPanelPf;
    }

    private SignupPage getSignupPage() {
        if (signupPf == null) {
            signupPf = testContextSetup.pageObjectManager.getSignupPage();
        }
        return signupPf;
    }

    @Given("user is on login page")
    public void user_is_on_login_page() {
        String url = testContextSetup.configFileReader.getProperty("loginPageUrl");
        testContextSetup.driver.get(url);
        logger.info("Navigated to login page: {}", url);
        Assert.assertEquals(testContextSetup.driver.getTitle(), "Login Page", "User is not on the login page.");
    }

    @When("user enter a valid username and password")
    public void i_enter_a_valid_username_and_password(DataTable dataTable) {
        List<Map<String, String>> credentials = dataTable.asMaps(String.class, String.class);
        String username = credentials.get(0).get("username");
        String password = credentials.get(0).get("password");
        logger.info("Entering username '{}' and password '{}'", username, password);
        getLoginPage().setUsername(username);
        getLoginPage().setPassword(password);
    }

    @When("user enter a valid captcha")
    public void i_enter_a_valid_captcha() {
        String captcha = getLoginPage().getSecurityCheckTextElmt();
        logger.info("Entering valid captcha: {}", captcha);
        getLoginPage().setCaptchaElmt(captcha);
    }

    @When("user click on validate")
    public void user_click_on_validate() {
        logger.info("Clicking on validate button");
        getLoginPage().clickValidateElmt();
    }

    @When("user click on the login button")
    public void i_click_on_the_login_button() {
        logger.info("Clicking on login button");
        getLoginPage().clickLoginBtnElmt();
    }

    @When("user accepting the alert")
    public void i_am_accepting_the_alert()  {
        logger.info("Accepting alert popup");
        getLoginPage().handleAlertBox().accept();
    }

    @Then("user should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        logger.info("Verifying redirection to home page");
        Assert.assertEquals(testContextSetup.driver.getTitle(), "TrainReservation", "User not redirected to home page.");
    }

    @When("user enters a username as {string}")
    public void user_enters_a_username_as(String username) {
        logger.info("Entering username: {}", username);
        getLoginPage().setUsername(username);
    }

    @When("user enters a password as {string}")
    public void user_enters_a_password_as(String password) {
        logger.info("Entering password: {}", password);
        getLoginPage().setPassword(password);
    }

    @Then("username error message should be displayed")
    public void username_error_message_should_be_displayed() {
        String errMsg = getLoginPage().getUsernameErrorMsg();
        logger.info("Verifying username error message. Actual: {}", errMsg);
        boolean isValid = errMsg.equals("Username is wrong") || errMsg.equals("Username cannot be empty");
        Assert.assertTrue(isValid, "Username error message is not as expected. Received: " + errMsg);
    }

    @Then("password error message should be displayed")
    public void password_error_message_should_be_displayed() {
        String errMsg = getLoginPage().getPasswordErrorMsg();
        logger.info("Verifying password error message. Actual: {}", errMsg);
        boolean isValid = errMsg.equals("Password is wrong") || errMsg.equals("Password cannot be empty");
        Assert.assertTrue(isValid, "Password error message is not as expected. Received: " + errMsg);
    }

    @When("user enter a captcha as {string}")
    public void user_enter_a_captcha_as(String captcha) {
        logger.info("Entering captcha: {}", captcha);
        getLoginPage().setCaptchaElmt(captcha);
    }

    @Then("captcha error message should be displayed")
    public void captcha_error_message_should_be_displayed() throws InterruptedException {
        String errMsg = getLoginPage().getCaptchaErrorMsg();
        logger.info("Verifying captcha error message from alert. Actual: {}", errMsg);
        boolean isValid = errMsg.equals("invalid input") || errMsg.equals("Please Enter The code");
        Assert.assertTrue(isValid, "Captcha error message is not as expected. Received: " + errMsg);
        getLoginPage().handleAlertBox().accept();
    }

    @When("user click on click here to reset")
    public void user_click_on_click_here_to_reset() {
        logger.info("Clicking on 'Click here to reset'");
        getLoginPage().clickClickHereToReset();
    }

    @Then("user should be redirected to reset panel")
    public void user_should_be_redirected_to_reset_panel() {
        logger.info("Verifying redirection to reset panel. Current title: {}", getResetPanelPage().getTitleOfPage());
        Assert.assertEquals(getResetPanelPage().getTitleOfPage(), "Document", "Not redirected to reset panel.");
    }

    @When("user click on signup using google")
    public void user_click_on_signup_using_google() {
        logger.info("Clicking on 'Signup using Google'");
        parentWindowHandle = testContextSetup.driver.getWindowHandle();
        getLoginPage().clickGmailSignInElmt();

        logger.info("Switching to new window for Google signup");
        Set<String> allWindows = testContextSetup.driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindowHandle)) {
                testContextSetup.driver.switchTo().window(window);
                break;
            }
        }
    }

    @When("user should be redirected to singup using goggle page")
    public void user_should_be_redirected_to_singup_using_goggle_page() {
        String title = getSignupPage().getTitleOfPage();
        logger.info("Verifying signup page title. Actual: {}", title);
        Assert.assertNotEquals(title, "Login Page", "Did not switch to a new page.");
    }

    @When("user enter the email in signup page as {string}")
    public void user_enter_the_email_in_signup_page(String email) {
        logger.info("Entering email '{}' in signup page", email);
        getSignupPage().setEmail(email);
    }

    @When("click on next button in signup page")
    public void click_on_next_button_in_signup_page() {
        logger.info("Clicking 'Next' button on signup page and switching back to parent window");
        getSignupPage().clickNextBtn();
        testContextSetup.driver.switchTo().window(parentWindowHandle);
    }

    @When("user click on remember me button")
    public void user_click_on_remember_me_button() {
        logger.info("Clicking remember me checkbox.");
        getLoginPage().clickRememberMeElmt();
    }

    @When("user click on logout button")
    public void user_click_on_logout_button() {
        logger.error("Step 'user click on logout button' is intentionally failed as element is missing from the application.");
        Assert.fail("Logout button is not present on the home page per the application's design.");
    }

    @Then("user should be redirected to login page")
    public void user_should_be_redirected_to_login_page() {
        logger.info("Verifying redirection to login page after logout");
        Assert.assertEquals(testContextSetup.driver.getTitle(), "Login Page", "Not redirected to login page.");
    }

    @Then("user enters email as {string}")
    public void user_enters_email_as(String email) {
        logger.info("Entering email '{}' in reset panel", email);
        getResetPanelPage().setEmailElmt(email);
    }

    @Then("user click on reset password")
    public void user_click_on_reset_password() {
        logger.info("Clicking on reset password button");
        getResetPanelPage().clickResetPasswordBtnElmt();
    }

    @Then("user accepting the alert if any")
    public void user_accepting_the_alert_if_any() {
        Alert alert = getResetPanelPage().handleAlertBox();
        if (alert != null) {
            logger.info("Accepting alert on reset panel page.");
            alert.accept();
        } else {
            logger.info("No alert was present on reset panel page.");
        }
    }

    @Then("user get a password when email is valid and any error msg if email is invalid")
    public void user_get_a_password_when_email_is_valid_and_any_error_msg_if_email_is_invalid() {
        String message = getResetPanelPage().getMsgElmt();
        logger.info("Message received on reset panel: {}", message);
        Assert.assertFalse(message.isEmpty(), "No message was displayed on reset panel.");
    }
}