#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table


Feature: Train Search Functionality

  # Author: Gautami

  @validTrainSearch
  Scenario: Train search using a valid train number
    Given the user is on the Train Search page
    When the user enters train input "12801"
    Then train details containing "PURI - NEW DELHI Purushottam SF Express" should appear

  @invalidTrainNumber
  Scenario: No error message for invalid train number
    Given the user is on the Train Search page
    When the user enters train input "00000"
    Then an appropriate error message should be shown

  @stringInput
  Scenario: Train Search Field Does Not Accept Train Names (Strings)
    Given the user is on the Train Search page
    When the user enters train input "Rajdhani"
    Then an appropriate error message should be shown

  @editableTable
  Scenario: Table field cell names are editable
    Given the train results are displayed in a table
    Then the table fields should be not be editable
