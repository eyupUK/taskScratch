Feature: Clinics

  Background: Get the access token
    Given I enter email as "gianna@hightable.test" and password as "thedantonio1" and request GET method from "/api/auth"
    Then I should be able to get access token

    @TC001
  Scenario: Get an list of emails
    Given I enter the access token and request GET method with 2 as id number
    Then I should get a message about authorization as "Error: User does not have permissions"

    @TC002
  Scenario: Get clinics searching veterinary
    Given I enter the access token and request GET method with term parameter as "veterinary"
    Then I should get the following list
      | Continental Veterinary Clinic, Los Angeles, CA         |
      | Third Transfer Veterinary Clinic, Los Angeles, CA      |
