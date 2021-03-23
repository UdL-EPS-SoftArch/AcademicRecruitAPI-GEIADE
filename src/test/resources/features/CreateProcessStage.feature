Feature: Create ProcessStage
  In order to create the process stage
  As a admin
  I want to create a process stage

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Admin Created participant
    Given I login as "admin" with password "password"
    When I create a processStage with name "Ronda 1" and step 2
    Then The response code is 201
    And It has been created a processStage with name "Ronda 1" and step 2

  Scenario: Admin Created participant with invalid step
    Given I login as "admin" with password "password"
    When I create a processStage with name "Ronda 1" and step 5
    Then The response code is 400

  Scenario: Admin Created Process Stage associated to Selection Process
    Given I login as "admin" with password "password"
    And I create a selection process with vacancy "Profesor"
    When I create a processStage with name "Ronda 1" and step 2 associated to selection process with vacancy "Profesor"
    Then The response code is 201
    And It has been created a processStage with name "Ronda 1" and step 2 associated to selection process with vacancy "Profesor"
