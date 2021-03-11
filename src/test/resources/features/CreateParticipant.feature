Feature: Create Participant
  In order to create the participants
  As a admin
  I want to create a participant

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Admin Created participant
    Given I login as "admin" with password "password"
    When I create a participant with role "SECRETARY"
    Then The response code is 201
    And It has been created a participant with role "SECRETARY"
