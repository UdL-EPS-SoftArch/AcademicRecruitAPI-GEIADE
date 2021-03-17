Feature: Create Participant
  In order to create the participants
  As a admin
  I want to create a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    And There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Admin Created participant
    Given I login as "admin" with password "password"
    When I create a participant with role "SECRETARY"
    Then The response code is 201
    Given I login as "admin" with password "password"
    And It has been created a participant with role "SECRETARY"

  Scenario: User Created participant
    Given I login as "user" with password "password"
    When I create a participant with role "SECRETARY"
    Then The response code is 403