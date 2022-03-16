#Author: mibit@protonmail.com
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

  @single
  Scenario: Register new user
    Given I register as new user
    When I complete registration
    Then I get the message "Check your email"

  Scenario Outline: a new user with a long name attempts registration
    Given I want to register as new user with a <length> long name
    When I complete registration
    Then I get the message <message>

    Examples: 
      | length | message                                  |
      |    101 | "Please check your entry and try again." |
      |    100 | "Check your email"                       |
      |     99 | "Check your email"                       |

  Scenario: Register already registered user
    Given I register as new user
    And I complete registration
    And I register as the same user
    And I complete registration
    Then I get the error message "Another user with this username already exists. Maybe it's your evil twin. Spooky."
