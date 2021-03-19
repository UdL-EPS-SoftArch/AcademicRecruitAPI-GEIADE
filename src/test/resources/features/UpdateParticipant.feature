Feature: Update Participant
  In order to modify the participants
  As a admin
  I want to update a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And There is a registered user with username "user" and password "password" and email "user@sample.app"


  Scenario: Admin updates role
    Given I login as "admin" with password "password"
    And I create a participant with role "SECRETARY"
    When I change the role of the participant with id "1" to "PRESIDENT"
    Then The response code is 200
    And The previously updated participant has now role "PRESIDENT"

  Scenario: User updates role
    Given I login as "user" with password "password"
    And I create a participant with role "SECRETARY"
    When I change the role of the participant with id "1" to "PRESIDENT"
    Then The response code is 403