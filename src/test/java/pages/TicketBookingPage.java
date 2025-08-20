package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TicketBookingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id="travelFrom") public WebElement fromCity;
    @FindBy(id="travelTo") public WebElement toCity;
    @FindBy(id="departure") public WebElement departureDate;
    @FindBy(id="selectclass") public WebElement travelClass;
    @FindBy(id="name") public WebElement name;
    @FindBy(id="email") public WebElement email;
    @FindBy(id="phone") public WebElement phone;
    @FindBy(id="ticket-class-count") public WebElement passengerCount;
    @FindBy(css="#ticket-class-increase i.fas.fa-plus") public WebElement increasePassengerIcon;
    @FindBy(css="#ticket-class-decrease i.fas.fa-minus") public WebElement decreasePassengerIcon;
    @FindBy(id="book-now") public WebElement bookNowBtn;
    @FindBy(id="reset-now") public WebElement resetButton;
    @FindBy(id="subtotal-amount") public WebElement subtotal;
    @FindBy(id="vat-amount") public WebElement vat;
    @FindBy(id="total-amount") public WebElement total;
    @FindBy(id="errfn") public WebElement commonErrorMessage;
    @FindBy(id="bookingconfirm") public WebElement bookingConfirmDiv;

    public TicketBookingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillBookingForm(String from, String to, String date, String classType,
                                String pname, String mail, String ph, String count){
        fromCity.clear(); fromCity.sendKeys(from);
        toCity.clear(); toCity.sendKeys(to);
        departureDate.clear(); departureDate.sendKeys(date);
        name.clear(); name.sendKeys(pname);
        email.clear(); email.sendKeys(mail);
        phone.clear(); phone.sendKeys(ph);

        Select select = new Select(travelClass);
        try {
            if (classType != null && !classType.isEmpty()) {
                select.selectByValue(classType);
            } else {
                select.selectByValue("");
            }
        } catch (NoSuchElementException e) {
            System.err.println("Warning: Class type '" + classType + "' not found.");
        }

        int targetCount = 0;
        boolean shouldAttemptClickOperations = true;

        try {
            int parsedCount = Integer.parseInt(count);
            if (parsedCount >= 0 && parsedCount <= 10) { // Assuming 0-10 is a typical clickable range
                targetCount = parsedCount;
            } else {
                shouldAttemptClickOperations = false;
            }
        } catch (NumberFormatException e) {
            shouldAttemptClickOperations = false;
        }

        if (!shouldAttemptClickOperations) {
            System.err.println("Info: Passenger count '" + count + "' is not suitable for button clicks. Sending keys directly for application validation.");
            passengerCount.clear();
            passengerCount.sendKeys(count);
            ((JavascriptExecutor)driver).executeScript("arguments[0].dispatchEvent(new Event('change'));", passengerCount);
            return;
        }

        wait.until(ExpectedConditions.visibilityOf(passengerCount));
        int currentCount = 0;
        try {
            currentCount = Integer.parseInt(passengerCount.getAttribute("value"));
        } catch (NumberFormatException e) {
            System.err.println("Warning: Initial passenger count field value '" + passengerCount.getAttribute("value") + "' is not a valid number.");
        }

        if (targetCount > currentCount) {
            int clicks = targetCount - currentCount;
            for (int i = 0; i < clicks; i++) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", increasePassengerIcon);
                    wait.until(ExpectedConditions.elementToBeClickable(increasePassengerIcon));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", increasePassengerIcon);
                } catch (Exception e) {
                    System.err.println("Failed to click '+' icon on attempt " + (i + 1) + ": " + e.getMessage());
                    try{currentCount=Integer.parseInt(passengerCount.getAttribute("value"));}catch(NumberFormatException ex){}
                    if(targetCount > currentCount){i--;}
                }
            }
        } else if (targetCount < currentCount) {
            int clicks = currentCount - targetCount;
            for (int i = 0; i < clicks; i++) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", decreasePassengerIcon);
                    wait.until(ExpectedConditions.elementToBeClickable(decreasePassengerIcon));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", decreasePassengerIcon);
                } catch (Exception e) {
                    System.err.println("Failed to click '-' icon on attempt " + (i + 1) + ": " + e.getMessage());
                    try{currentCount=Integer.parseInt(passengerCount.getAttribute("value"));}catch(NumberFormatException ex){}
                    if(targetCount < currentCount){i--;}
                }
            }
        }
        wait.until(ExpectedConditions.attributeToBe(passengerCount, "value", String.valueOf(targetCount)));
        System.out.println("Passenger count set to: " + passengerCount.getAttribute("value"));
    }

    public void clickIncreasePassenger(){
        performClick(increasePassengerIcon);
    }

    public void clickDecreasePassenger(){
        performClick(decreasePassengerIcon);
    }

    public void clickBookNow(){
        performClick(bookNowBtn);
    }

    public void clickReset(){
        performClick(resetButton);
    }

    private void performClick(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + element.toString() + " - " + e.getMessage(), e);
        }
    }
}
