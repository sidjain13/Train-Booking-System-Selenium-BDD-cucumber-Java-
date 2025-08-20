Feature: Train Enquiry Contact Form
  I want to send enquiry about trains using contact form

  Scenario: Open enquiry page
    Given I open the enquiry page "https://webapps.tekstac.com/SeleniumApp1/TrainReservation/contactus.html"
    Then I should see "For Train Enquiry Please Contact Us" text
    And I should see the enquiry form

  Scenario: Check enquiry form fields
    Given I am on the enquiry page
    Then I should see "Full name" field
    And I should see "Email Address" field
    And I should see "Message" field
    And I should see "Send" button

  Scenario: Submit enquiry with valid details
    Given I am on the enquiry page
    When I enter "John Smith" in full name field
    And I enter "john@email.com" in email field
    And I enter "I want to enquire about train timings from Delhi to Mumbai" in message field
    And I click send button
    Then enquiry should be submitted successfully

  Scenario: Submit enquiry without name
    Given I am on the enquiry page
    When I enter "john@email.com" in email field
    And I enter "My enquiry message" in message field
    And I click send button
    Then I should see error for name field

  Scenario: Submit enquiry without email
    Given I am on the enquiry page
    When I enter "John Smith" in full name field
    And I enter "My enquiry message" in message field
    And I click send button
    Then I should see error for email field

  Scenario: Submit enquiry without message
    Given I am on the enquiry page
    When I enter "John Smith" in full name field
    And I enter "john@email.com" in email field
    And I click send button
    Then I should see error for message field

  Scenario: Submit empty enquiry form
    Given I am on the enquiry page
    When I click send button
    Then I should see validation errors