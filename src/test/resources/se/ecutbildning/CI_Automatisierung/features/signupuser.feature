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

  Scenario Outline: Signup a new user with the signup form
    Given a <lengthtype> name length
    And a <email status> email
    When I complete registration
    Then I get the <expected> message

    Examples: 
      | lengthtype | email status | expected               |
      | "valid"    | "normal"     | "Check your email"     |
      | "too long" | "normal"     | "Enter a value less"   |
      | "zero"     | "missing"    | "Please enter a value" |
      
      
       #Rule: Supplying an existing email gives a specific error
