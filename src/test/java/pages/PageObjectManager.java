package pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private LoginPage loginPage;
    private EnquiryPage enquiryPage;
    private ResetPanelPage resetPanelPage;
    private SignupPage signupPage;
    private TicketBookingPage ticketBookingPage;
    private TrainSearchPage trainSearchPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }
    
    public EnquiryPage getEnquiryPage() {
        return (enquiryPage == null) ? enquiryPage = new EnquiryPage(driver) : enquiryPage;
    }

    public ResetPanelPage getResetPanelPage() {
        return (resetPanelPage == null) ? resetPanelPage = new ResetPanelPage(driver) : resetPanelPage;
    }

    public SignupPage getSignupPage() {
        // This page opens in a new window, special handling might be needed in steps
        return (signupPage == null) ? signupPage = new SignupPage(driver) : signupPage;
    }

    public TicketBookingPage getTicketBookingPage() {
        return (ticketBookingPage == null) ? ticketBookingPage = new TicketBookingPage(driver) : ticketBookingPage;
    }

    public TrainSearchPage getTrainSearchPage() {
        return (trainSearchPage == null) ? trainSearchPage = new TrainSearchPage(driver) : trainSearchPage;
    }
}