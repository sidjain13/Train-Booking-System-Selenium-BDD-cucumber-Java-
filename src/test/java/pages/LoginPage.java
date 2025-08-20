package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.NoAlertPresentException;

public class LoginPage {
	
	public WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	
	@FindBy(id="username") WebElement usernameElmt;
	@FindBy(id="password") WebElement passwordElmt;
	@FindBy(id="code") WebElement securityCheckTextElmt;
	@FindBy(id="captcha") WebElement captchaElmt;
	@FindBy(id="captchaBtn") WebElement validateElmt;
	@FindBy(id="submit") WebElement loginBtnElmt;
	@FindBy(id="usernameErr") WebElement usernameErrElmt;
	@FindBy(id="passwordErr") WebElement passwordErrElmt;
	@FindBy(linkText="Click here to reset it") WebElement clickHereToResetElmt;
	@FindBy(id="gmailsignin") WebElement gmailSignInElmt;
	@FindBy(id="remember_me") WebElement rememberMeElmt;
	

	
	
	public void setUsername(String uname) {
		usernameElmt.sendKeys(uname);
	}


	public void setPassword(String pass) {
		passwordElmt.sendKeys(pass);
	}
	

	public String getSecurityCheckTextElmt() {
		return securityCheckTextElmt.getText();
	}
	

	public void setCaptchaElmt(String cpt) {
		captchaElmt.sendKeys(cpt);
	}

	public void clickValidateElmt() {
		validateElmt.click();
	}
	

	public void clickLoginBtnElmt() {
		loginBtnElmt.click();
	}
	
	public Alert handleAlertBox() throws NoAlertPresentException{
		return driver.switchTo().alert();
	}
	
	
	
	public String getUsernameErrorMsg() {
		return usernameErrElmt.getText();
	}
	

	public String getPasswordErrorMsg() {
		return passwordErrElmt.getText();
	}
	
	public String getCaptchaErrorMsg() throws InterruptedException {
		Alert a=handleAlertBox();
		return a.getText();
	}
	
	public void clickClickHereToReset() {
		clickHereToResetElmt.click();
	}

	
	public void clickGmailSignInElmt() {
		gmailSignInElmt.click();	
	}
	
	
	public void clickRememberMeElmt() {
		rememberMeElmt.click();
	}
	
	
	
}
