Feature: Create ProcessStage
  In order to create the process stage
  As a admin
  I want to create a process stage

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given I login as "admin" with password "password"
    And I create a selection process with vacancy "Professor de Mates"

  Scenario: Admin Created Process Stage
    When  I create a processStage with name "Ronda 1" and step 2 associated to selection process with vacancy "Professor de Mates"
    Then The response code is 201
    And It has been created a processStage with name "Ronda 1" and step 2
    And It has been created a processStage associated to selection process with vacancy "Professor de Mates"

  Scenario: Admin Created Process Stage with invalid selection process
    Given I login as "admin" with password "password"
    When I create a processStage with name "Ronda 1" and step 2
    Then The response code is 403

  Scenario: Admin Created Process Stage with invalid step
    Given I login as "admin" with password "password"
    When I create a processStage with name "Ronda 1" and step 5 associated to selection process with vacancy "Professor de Mates"
    Then The response code is 400

  Scenario: Admin Created multiple Process Stages
    When  I create a processStage with name "Ronda 1" and step 1 associated to selection process with vacancy "Professor de Mates"
    Then The response code is successful
    When  I create a processStage with name "Ronda 1" and step 2 associated to selection process with vacancy "Professor de Mates"
    Then The response code is successful
    When  I create a processStage with name "Ronda 1" and step 3 associated to selection process with vacancy "Professor de Mates"
    Then The response code is successful
    When  I create a processStage with name "Ronda 1" and step 2 associated to selection process with vacancy "Professor de Mates"
    Then The response code is 403


