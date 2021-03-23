Feature: Candidate Delete
  In order to control candidates
  As an administrator
  I want to delete existing candidates

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a candidate with name "name"

  Scenario: Admin deletes participant
    Given There is a candidate with name "lluc"
    When I delete a candidate with name "lluc"
    Then The response code is 204
    And The previously deleted candidate doesn't exist
