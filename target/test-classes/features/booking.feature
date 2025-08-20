Feature: Train Ticket Booking

  Background:
    Given User is on Ticket Booking page

  Scenario Outline: Validate Required Fields - <test_case>
    When User leaves required fields blank and clicks Book
    Then Error messages should be displayed

    Examples:
      | test_case    |
      | Empty Fields |

  Scenario Outline: Phone Number Format Validation - <phone_number>
    When User enters invalid phone number "<phone_number>"
    Then Phone number format validation should trigger

    Examples:
      | phone_number   |
      | abc123         |
      | 123            |
      | 123456789      |
      | +91-1234567890 |

  Scenario Outline: Date Field Past Date Validation - <past_date>
    When User enters past date "<past_date>" in date field
    Then Date validation message should appear

    Examples:
      | past_date  |
      | 01/01/2022 |
      | 15/03/2023 |
      | 01/01/1999 |

  Scenario Outline: Date Field Invalid String Input - <invalid_date_string>
    When User enters invalid string "<invalid_date_string>" in date field
    Then Date validation message should appear for invalid format

    Examples:
      | invalid_date_string |
      | a@123           |
      | notadate            |
      | 32/13/2025          |

  Scenario Outline: Invalid Number of Passengers Input - <passenger_count_input>
    When User enters invalid passenger count "<passenger_count_input>"
    Then Passenger count validation message should appear

    Examples:
      | passenger_count_input |
      | -1                    | 
      | abc                   |
      | 100                   | 

  Scenario: Check Reset Button Functionality
    When User fills some details and clicks Reset
    Then All fields should be cleared

  