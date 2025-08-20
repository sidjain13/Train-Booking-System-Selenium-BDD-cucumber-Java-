package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EnquiryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "message")
    private WebElement messageInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "nameError")
    private WebElement nameError;

    @FindBy(id = "emailError")
    private WebElement emailError;

    @FindBy(id = "messageError")
    private WebElement messageError;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    public EnquiryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterMessage(String message) {
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    // **FIX:** New methods to check for specific error messages
    public boolean isNameErrorVisible() {
        try {
            return nameError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailErrorVisible() {
        try {
            return emailError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMessageErrorVisible() {
        try {
            return messageError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }
}