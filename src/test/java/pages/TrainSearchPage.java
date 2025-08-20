package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class TrainSearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "myInput")
    private WebElement searchInput;

    @FindBy(xpath = "//table[@id='myTable']/tbody/tr")
    private List<WebElement> allTableRows;
    
    // first cell of the first row of table
    @FindBy(xpath = "//table[@id='myTable']/tbody/tr[1]//input")
    private WebElement firstCellInputElement;

    public TrainSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    public void enterTrainInput(String trainNumber) {
        searchInput.clear();
        searchInput.sendKeys(trainNumber);
    }

    public String getVisibleTrainDetails() {
        // It stores the table rows which does not have display:none in style (Only visible rows when search s maid)
        wait.until(d -> allTableRows.stream().anyMatch(row -> !row.getAttribute("style").contains("display: none")));

        for (WebElement row : allTableRows) {
            // A visible row will not have the 'display: none' style
            if (!row.getAttribute("style").contains("display: none")) {
                return row.getText();
            }
        }
        return ""; // if no trains found
    }
    
    public boolean areTableFieldsNotEditable() {
        // The fields are not editable if they are not enabled.
        return !firstCellInputElement.isEnabled();
    }
    
    public boolean areAnyResultsVisible() {
        for (WebElement row : allTableRows) {
            if (!row.getAttribute("style").contains("display: none")) {
                return true;
            }
        }
        return false;
    }
}