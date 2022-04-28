#Author: mibit@protonmail.com
#Keywords Summary : mailchimp signup functionality
#""" (Doc Strings)
Feature: user signup at Mailchimp
  In order to get an account
  As an individual 
  I want to sign up as a new user 
  
  Rule: Supplying valid user details results in signup success
  
  Rule: Using a name longer than one hundred characters gives a specific error
  
  Rule: Forgetting to add an email address gives a specific error
  
  Rule: Supplying an existing email gives a specific error

 Scenario Outline: Signup a new user with the signup form
    Given a <status> email
    When I complete registration
    Then I get the <expected> message

   Examples: 
      | status     | expected               |
      | "normal"   | "Check your email"     |
      | "too long" | "Enter a value less"   |
      | "missing"  | "Please enter a value" |

  Scenario Outline: Register already registered user
    Given a "normal" email
    And I complete registration
    And I register as the same user
    When I complete registration
    Then I get the "Another user with this username already exists" message
    
#    Examples:
#    |norm		|
#    |normal	|
   
