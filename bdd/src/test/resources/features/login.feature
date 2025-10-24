Feature: Login functionality
  As a user
  I want to login to the application
  So that I can access my account

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I login with username "demo" and password "mode"
    Then I should see the welcome page