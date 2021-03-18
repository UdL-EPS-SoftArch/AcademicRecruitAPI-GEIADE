Feature: Delete Participant
  In order to delete the participants
  As a admin
  I want to delete a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Admin deletes participant
    Given I login as "admin" with password "password"
    And I create a participant with role "SECRETARY"
    When I delete a participant with id "1"
    Then The response code is 204
    And The previously deleted participant doesn't exist

  Scenario: User deletes participant
    Given I login as "admin" with password "password"
    And I create a participant with role "SECRETARY"
    Given I login as "user" with password "password"
    When I delete a participant with id "1"
    Then The response code is 403