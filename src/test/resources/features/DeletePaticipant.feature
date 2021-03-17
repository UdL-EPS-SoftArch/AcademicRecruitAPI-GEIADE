Feature: Delete Participant
  In order to delete the participants
  As a admin
  I want to delete a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a participant with role "SECRETARY"

  Scenario: Admin deletes participant
    When I delete a participant with id "1"
    Then The response code is 204
    And The previously deleted participant doesn't exist
    And The response code is 404