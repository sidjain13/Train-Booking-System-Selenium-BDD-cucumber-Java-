package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private WebDriver driver;
    
    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//div[@id='mailbox']/input") 
    private WebElement emailElmt;
    
    @FindBy(id="button2") 
    private WebElement nextBtnElmt;

    public String getTitleOfPage() {
        return driver.getTitle();
    }

    public void setEmail(String email) {
        emailElmt.sendKeys(email);
    }

    public void clickNextBtn() {
        nextBtnElmt.click();
    }
}