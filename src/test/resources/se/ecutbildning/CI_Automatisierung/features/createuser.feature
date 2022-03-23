#Author: mibit@protonmail.com0
#Keywords Summary : mailchimp register functionality
#Feature: mailchimp register user
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: register user
  I register as user

  Scenario: Register new user
    Given I register as new user
    When I complete registration
    Then I get the message "Check your email"

  Scenario Outline: Registration with a long name
    Given I want to register as new user with a <length> long name
    When I complete registration
    Then I get the message <message>

    Examples: 
      | length | message            |
      |     99 | "Check your email" |
      |    100 | "Check your email" |
      |     50 | "Check your email" |

  Scenario Outline: Registration with a far too long name
    Given I want to register as new user with a <length> long name
    When I complete registration
    Then I get the error message "Please check your entry and try again."

    Examples: 
      | length |
      |    101 |
      |    105 |
      |    134 |

  Scenario: Register already registered user
    Given I register as new user
    And I complete registration
    And I register as the same user
    When I complete registration
    Then I get the error message "Please check your entry and try again."
#    Then I get the error message "Another user with this username already exists. Maybe it's your evil twin. Spooky."

  Scenario: a new user forgets entering the email adress when registering
    Given I want to register as new user with a 0 long name
    When I complete registration
    Then I get the error message "Please check your entry and try again."
