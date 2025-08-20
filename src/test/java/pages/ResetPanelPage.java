package pages;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResetPanelPage {

	public WebDriver driver;
	
	public ResetPanelPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	
	@FindBy(id="emailID") WebElement emailElmt;
	@FindBy(xpath="//button") WebElement resetPasswordBtnElmt;
	@FindBy(id="resetErr") WebElement msgElmt;
	
	
    
	public String getTitleOfPage() {
		return driver.getTitle();
	}
    
	public void setEmailElmt(String email) {
		emailElmt.sendKeys(email);
	}
	
	public void clickResetPasswordBtnElmt() {
		resetPasswordBtnElmt.click();
	}
	
	public String getMsgElmt() {
		return msgElmt.getText();
	}
	
	public Alert handleAlertBox() {
	
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		        return wait.until(ExpectedConditions.alertIsPresent());
		    } 
		    catch (Exception e) {
		        return null;
		    }


	}
	
	
	
}
