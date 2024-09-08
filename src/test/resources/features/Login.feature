Feature: Login Functionality

  Background:
    Given the user is on the login page

  @smoke @regression
  Scenario Outline: Successful Login with valid credentials
    When the user logs in as "<role>"
    And the user clicks on the login button
    Then the user should be redirected to the dashboard
    When the user clicks on the logout button
    Then the user is navigated to login page
  Examples:
   | role  |
   | admin |
   | dev   |
   | test  |

  @regression
  Scenario: Unsuccessful Login with invalid credentials
    When the user enters invalid username and password
    And the user clicks on the login button
    Then the user should not be redirected to the dashboard

  @regression
  Scenario: Unsuccessful Login with blank username and password
    When the user leaves the username and password fields blank
    And the user clicks on the login button
    Then the user should not be redirected to the dashboard
    
  @smoke @duplicate
  Scenario: Successful Login with valid credentials1
    When the user logs in as "admin"
    And the user clicks on the login button
    Then the user should be redirected to the dashboard
    When the user clicks on the logout button
    Then the user is navigated to login page

  @regression @duplicate
  Scenario: Unsuccessful Login with invalid credentials1
    When the user enters invalid username and password
    And the user clicks on the login button
    Then the user should not be redirected to the dashboard

  @regression @duplicate
  Scenario: Unsuccessful Login with blank username and password1
    When the user leaves the username and password fields blank
    And the user clicks on the login button
    Then the user should not be redirected to the dashboard
    