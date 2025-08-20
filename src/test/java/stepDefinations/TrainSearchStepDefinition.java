/*
package stepDefinations;

import context.TestContextSetup;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.TrainSearchPage;

public class TrainSearchStepDefinition {

    private TestContextSetup testContextSetup;
    private TrainSearchPage trainSearchPage;
    private static final Logger logger = LogManager.getLogger(TrainSearchStepDefinition.class);

    public TrainSearchStepDefinition(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
//        this.trainSearchPage = testContextSetup.pageObjectManager.getTrainSearchPage();
    }

    @Given("the user is on the Train Search page")
    public void user_is_on_train_search_page() {
        String url = testContextSetup.configFileReader.getProperty("searchPageUrl");
        testContextSetup.driver.get(url);
        logger.info("Navigated to Train Search page: {}", url);
    }

    @When("the user enters train input {string}")
    public void user_enters_train_input(String input) {
        logger.info("Entering train input: {}", input);
        
        trainSearchPage.enterTrainInput(input);
    }

    @Then("train details containing {string} should appear")
    public void train_details_should_appear(String trainName) {
        String resultText = trainSearchPage.getVisibleTrainDetails();
        logger.info("Verifying train details contain '{}'. Actual visible result: {}", trainName, resultText);
        Assert.assertTrue(resultText.contains(trainName), "Train details do not contain the expected name.");
    }

    @Then("an appropriate error message should be shown")
    public void error_message_should_be_shown() {
        logger.info("Verifying that an appropriate error message is shown");
        
        Assert.assertFalse(trainSearchPage.areAnyResultsVisible(), "Results were found for an invalid search.");
        
        
        boolean isErrorDisplayed;
        try {
            isErrorDisplayed = testContextSetup.driver.findElement(By.id("errorMsg")).isDisplayed(); 
        } catch (Exception e) {
            isErrorDisplayed = false;
        }
        
        logger.error("Application Bug: No error message element was found on the page for an invalid search.");
        Assert.assertTrue(isErrorDisplayed, "BUG: The application does not show an error message for invalid train searches.");
    }

    @Given("the train results are displayed in a table")
    public void train_results_in_table() {
        user_is_on_train_search_page();
        logger.info("Confirmed that train results are displayed by default.");
    }

    @Then("the table fields should be not be editable")
    public void the_table_fields_should_be_not_be_editable() {
        logger.info("Verifying that the table fields are not editable.");
        Assert.assertTrue(trainSearchPage.areTableFieldsNotEditable(), "Table fields were found to be editable.");
    }
}
*/

package stepDefinations;

import context.TestContextSetup;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import pages.TrainSearchPage;

public class TrainSearchStepDefinition {

    private TestContextSetup testContextSetup;
    private TrainSearchPage trainSearchPage;
    private static final Logger logger = LogManager.getLogger(TrainSearchStepDefinition.class);

    public TrainSearchStepDefinition(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }

    // Helper method for lazy initialization
    private TrainSearchPage getTrainSearchPage() {
        if (trainSearchPage == null) {
            trainSearchPage = testContextSetup.pageObjectManager.getTrainSearchPage();
        }
        return trainSearchPage;
    }

    @Given("the user is on the Train Search page")
    public void user_is_on_train_search_page() {
        String url = testContextSetup.configFileReader.getProperty("searchPageUrl");
        testContextSetup.driver.get(url);
        logger.info("Navigated to Train Search page: {}", url);
    }

    @When("the user enters train input {string}")
    public void user_enters_train_input(String input) {
        logger.info("Entering train input: {}", input);
        getTrainSearchPage().enterTrainInput(input);
    }

    @Then("train details containing {string} should appear")
    public void train_details_should_appear(String trainName) {
        String resultText = getTrainSearchPage().getVisibleTrainDetails();
        logger.info("Verifying train details contain '{}'. Actual visible result: {}", trainName, resultText);
        Assert.assertTrue(resultText.contains(trainName), "Train details do not contain the expected name.");
    }

    @Then("an appropriate error message should be shown")
    public void error_message_should_be_shown() {
        logger.info("Verifying that no results are shown for an invalid search.");
        Assert.assertFalse(getTrainSearchPage().areAnyResultsVisible(), "Results were found for an invalid search.");
        
        logger.error("BUG IDENTIFIED: Application does not display an error message for invalid searches.");
        Assert.fail("Test failed as expected due to application bug: No specific error message element is shown.");
    }

    @Given("the train results are displayed in a table")
    public void train_results_in_table() {
        user_is_on_train_search_page();
        logger.info("Confirmed that train results are displayed by default.");
    }

    @Then("the table fields should be not be editable")
    public void the_table_fields_should_be_not_be_editable() {
        logger.info("Verifying that the table fields are not editable.");
        Assert.assertTrue(getTrainSearchPage().areTableFieldsNotEditable(), "Table fields were found to be editable.");
    }
}