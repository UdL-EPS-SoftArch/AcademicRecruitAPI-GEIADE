Feature: Update Participant
  In order to modify the participants
  As a admin
  I want to update a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And I login as "admin" with password "password"
    And I create a participant with role 0

  Scenario: Admin updates role
    When I change the role of the participant with id "1" to 1
    Then The response code is 200
    And The previously updated participant has now role 1
